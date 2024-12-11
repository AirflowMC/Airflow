package me.glicz.airflow.api.scheduler;

import me.glicz.airflow.api.Server;
import me.glicz.airflow.api.ServerAware;
import me.glicz.airflow.api.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public interface Task extends ServerAware {
    int getId();

    boolean isAsync();

    int getDelay();

    @NotNull Plugin getPlugin();

    void cancel();

    @Override
    default Server getServer() {
        return getPlugin().getServer();
    }

    interface Builder {
        @NotNull Builder async();

        @NotNull Builder delay(@Range(from = 0, to = Integer.MAX_VALUE) int ticks);

        @NotNull Task schedule();
    }
}
