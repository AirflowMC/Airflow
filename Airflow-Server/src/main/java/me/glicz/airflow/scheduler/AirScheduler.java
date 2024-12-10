package me.glicz.airflow.scheduler;

import me.glicz.airflow.api.plugin.Plugin;
import me.glicz.airflow.api.scheduler.RepeatingTask;
import me.glicz.airflow.api.scheduler.Scheduler;
import me.glicz.airflow.api.scheduler.Task;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class AirScheduler implements Scheduler {
    private static final AtomicInteger TASK_COUNTER = new AtomicInteger(1);
    private final Map<Integer, AirTask> taskMap = new ConcurrentHashMap<>();
    private final Plugin plugin;

    public AirScheduler(Plugin plugin) {
        this.plugin = plugin;
    }

    void tick() {
        taskMap.values().forEach(task -> {
            try {
                task.tick();
            } catch (Exception e) {
                task.getPlugin().getLogger().error("Failed to execute task #{}", task.getId(), e);
            }
        });
    }

    AirTask schedule(AirTask.Builder builder) {
        AirTask task = builder.build(TASK_COUNTER.getAndIncrement());
        taskMap.put(task.getId(), task);
        return task;
    }

    @Override
    public @NotNull Plugin getPlugin() {
        return plugin;
    }

    @Override
    public Task.@NotNull Builder taskBuilder(@NotNull Consumer<Task> action) {
        return new AirTask.Builder(this, plugin, action);
    }

    @Override
    public RepeatingTask.@NotNull Builder repeatingTaskBuilder(@NotNull Consumer<Task> action) {
        return new AirRepeatingTask.Builder(this, plugin, action);
    }

    @Override
    public void cancelTask(int id) {
        this.taskMap.remove(id);
    }

    @Override
    public Executor getSyncExecutor() {
        return r -> taskBuilder(r).schedule();
    }

    @Override
    public Executor getAsyncExecutor() {
        return r -> taskBuilder(r).async().schedule();
    }
}
