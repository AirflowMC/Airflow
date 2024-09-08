package me.glicz.airflow.api;

import me.glicz.airflow.api.command.Commands;
import me.glicz.airflow.api.command.sender.RemoteCommandSender;
import me.glicz.airflow.api.command.sender.ServerCommandSender;
import me.glicz.airflow.api.event.command.CommandsRegisterEvent;
import me.glicz.airflow.api.permission.Permissions;
import org.jetbrains.annotations.NotNull;

public interface Server extends ServerAware, ServerInfoProvider {
    @NotNull String getServerBrandName();

    @NotNull ServerCommandSender getServerCommandSender();

    @NotNull RemoteCommandSender getRemoteCommandSender();

    /**
     * Exposed in case a plugin wants to dynamically manage commands.
     * Unless you know what you're doing, you should stick to {@link CommandsRegisterEvent}.
     *
     * @return commands registry
     */
    @NotNull Commands getCommands();

    @NotNull Permissions getPermissions();

    @Override
    default Server getServer() {
        return this;
    }
}
