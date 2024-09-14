package me.glicz.airflow.scheduler;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import me.glicz.airflow.api.plugin.Plugin;
import me.glicz.airflow.api.scheduler.Task;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class AirTask implements Task {
    static final Executor ASYNC_EXECUTOR = Executors.newCachedThreadPool(
            new ThreadFactoryBuilder().setNameFormat("Server Async Scheduler - %d").build()
    );
    final Consumer<Task> action;
    private final Plugin plugin;
    private final boolean async;
    private final int delay, id;
    int tick;

    AirTask(Plugin plugin, Consumer<Task> action, boolean async, int delay, int id) {
        this.plugin = plugin;
        this.action = action;
        this.async = async;
        this.delay = delay;
        this.id = id;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public boolean isAsync() {
        return this.async;
    }

    @Override
    public int getDelay() {
        return this.delay;
    }

    @Override
    public @NotNull Plugin getPlugin() {
        return this.plugin;
    }

    @Override
    public void cancel() {
        this.plugin.getScheduler().cancelTask(this.id);
    }

    void tick() {
        if (++tick >= delay) {
            cancel();
            run();
        }
    }

    void run() {
        if (this.async) {
            ASYNC_EXECUTOR.execute(() -> action.accept(this));
            return;
        }

        action.accept(this);
    }

    static class Builder implements Task.Builder {
        final AirScheduler scheduler;
        final Plugin plugin;
        final Consumer<Task> action;
        int delay;
        boolean async;

        Builder(AirScheduler scheduler, Plugin plugin, Consumer<Task> action) {
            this.scheduler = scheduler;
            this.plugin = plugin;
            this.action = action;
        }

        AirTask build(int id) {
            return new AirTask(plugin, action, async, delay, id);
        }

        @Override
        public Task.@NotNull Builder async() {
            this.async = true;
            return this;
        }

        @Override
        public Task.@NotNull Builder delay(int ticks) {
            this.delay = ticks;
            return this;
        }

        @Override
        public @NotNull Task schedule() {
            return scheduler.schedule(this);
        }
    }
}
