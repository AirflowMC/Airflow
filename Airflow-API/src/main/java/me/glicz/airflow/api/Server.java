package me.glicz.airflow.api;

import me.glicz.airflow.api.command.ServerCommandSender;
import org.jetbrains.annotations.NotNull;

public interface Server extends ServerAware, ServerInfoProvider {
    @NotNull String getServerBrandName();

    @NotNull ServerCommandSender getServerCommandSender();

    @Override
    default Server getServer() {
        return this;
    }
}
