package me.glicz.airflow.api;

import me.glicz.airflow.api.command.Commands;
import me.glicz.airflow.api.command.sender.RemoteCommandSender;
import me.glicz.airflow.api.command.sender.ServerCommandSender;
import me.glicz.airflow.api.entity.living.Player;
import me.glicz.airflow.api.event.command.CommandsRegisterEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.UUID;

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

    @Nullable Player getPlayer(String name);

    @Nullable Player getPlayer(UUID uniqueId);

    @NotNull Collection<Player> getOnlinePlayers();

    @Override
    default Server getServer() {
        return this;
    }
}
