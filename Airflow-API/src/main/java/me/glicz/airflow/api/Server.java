package me.glicz.airflow.api;

import me.glicz.airflow.api.command.sender.RemoteCommandSender;
import me.glicz.airflow.api.command.sender.ServerCommandSender;
import org.jetbrains.annotations.NotNull;

public interface Server extends ServerAware, ServerInfoProvider {
    @NotNull String getServerBrandName();

    @NotNull ServerCommandSender getServerCommandSender();

    @NotNull RemoteCommandSender getRemoteCommandSender();

    @Override
    default Server getServer() {
        return this;
    }
}
