package me.glicz.airflow.api.scheduler;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public interface RepeatingTask extends Task {
    int getInterval();

    interface Builder extends Task.Builder {
        @NotNull Builder interval(@Range(from = 1, to = Integer.MAX_VALUE) int ticks);

        @Override
        @NotNull Builder delay(@Range(from = 0, to = Integer.MAX_VALUE) int ticks);

        @Override
        @NotNull Builder async();

        @Override
        @NotNull Task schedule();
    }
}
