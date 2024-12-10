package me.glicz.airflow;

import me.glicz.airflow.api.Server;
import me.glicz.airflow.api.command.Commands;
import me.glicz.airflow.api.command.sender.RemoteCommandSender;
import me.glicz.airflow.api.command.sender.ServerCommandSender;
import me.glicz.airflow.api.entity.living.Player;
import me.glicz.airflow.api.event.bus.ServerEventBus;
import me.glicz.airflow.api.permission.Permissions;
import me.glicz.airflow.api.plugin.PluginsLoader;
import me.glicz.airflow.api.properties.ServerProperties;
import me.glicz.airflow.api.service.Services;
import me.glicz.airflow.api.util.Version;
import me.glicz.airflow.command.sender.AirRemoteCommandSender;
import me.glicz.airflow.command.sender.AirServerCommandSender;
import me.glicz.airflow.scheduler.AirServerScheduler;
import me.glicz.airflow.util.MinecraftComponentSerializer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.UUID;

public class AirServer implements Server {
    public final Airflow airflow;
    public final DedicatedServer minecraftServer;
    public final AirServerScheduler serverScheduler;
    private final ServerCommandSender serverCommandSender;
    private final RemoteCommandSender remoteCommandSender;

    public AirServer(Airflow airflow, DedicatedServer minecraftServer) {
        this.airflow = airflow;
        this.minecraftServer = minecraftServer;

        this.serverScheduler = new AirServerScheduler(this);

        this.serverCommandSender = new AirServerCommandSender(this);
        this.remoteCommandSender = new AirRemoteCommandSender(this);
    }

    @Override
    public @NotNull String getServerBrandName() {
        return minecraftServer.getServerModName();
    }

    @Override
    public @NotNull ServerCommandSender getServerCommandSender() {
        return serverCommandSender;
    }

    @Override
    public @NotNull RemoteCommandSender getRemoteCommandSender() {
        return remoteCommandSender;
    }

    @Override
    public @NotNull Commands getCommands() {
        return minecraftServer.getCommands().airCommands;
    }

    @Override
    public @Nullable Player getPlayer(String name) {
        ServerPlayer player = minecraftServer.getPlayerList().getPlayerByName(name);
        if (player != null) {
            return player.getAirEntity();
        }

        return null;
    }

    @Override
    public @Nullable Player getPlayer(UUID uniqueId) {
        ServerPlayer player = minecraftServer.getPlayerList().getPlayer(uniqueId);
        if (player != null) {
            return player.getAirEntity();
        }

        return null;
    }

    @Override
    public @NotNull Collection<Player> getOnlinePlayers() {
        return minecraftServer.getPlayerList().getPlayers().stream()
                .<Player>map(ServerPlayer::getAirEntity)
                .toList();
    }

    @Override
    public @NotNull Permissions getPermissions() {
        return airflow.permissions;
    }

    @Override
    public @NotNull Version getServerVersion() {
        return airflow.version;
    }

    @Override
    public @NotNull ServerProperties getServerProperties() {
        return airflow.serverProperties;
    }

    @Override
    public @NotNull PluginsLoader getPluginsLoader() {
        return airflow.pluginLoader;
    }

    @Override
    public @NotNull ServerEventBus getServerEventBus() {
        return airflow.serverEventBus;
    }

    @Override
    public @NotNull Services getServices() {
        return airflow.services;
    }

    public MinecraftComponentSerializer componentSerializer() {
        return new MinecraftComponentSerializer(minecraftServer::registryAccess);
    }
}
