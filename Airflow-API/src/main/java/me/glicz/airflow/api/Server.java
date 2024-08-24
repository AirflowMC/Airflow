package me.glicz.airflow.api;

import me.glicz.airflow.api.command.ServerCommandSource;
import org.jetbrains.annotations.NotNull;

public interface Server extends ServerAware, ServerInfoProvider {
    @NotNull String getServerBrandName();

    @NotNull ServerCommandSource getServerCommandSource();

    @Override
    default Server getServer() {
        return this;
    }
}
