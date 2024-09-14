package me.glicz.airflow.api.scheduler;

import me.glicz.airflow.api.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;
import java.util.function.Consumer;

public interface Scheduler {
    @NotNull Plugin getPlugin();

    default Task.@NotNull Builder taskBuilder(@NotNull Runnable action) {
        return taskBuilder(task -> action.run());
    }

    Task.@NotNull Builder taskBuilder(@NotNull Consumer<Task> action);

    default RepeatingTask.@NotNull Builder repeatingTaskBuilder(@NotNull Runnable action) {
        return repeatingTaskBuilder(task -> action.run());
    }

    RepeatingTask.@NotNull Builder repeatingTaskBuilder(@NotNull Consumer<Task> action);

    void cancelTask(int id);

    Executor getSyncExecutor();

    Executor getAsyncExecutor();
}
