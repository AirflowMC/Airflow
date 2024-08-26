package me.glicz.airflow;

import me.glicz.airflow.api.Server;
import me.glicz.airflow.api.command.sender.RemoteCommandSender;
import me.glicz.airflow.api.command.sender.ServerCommandSender;
import me.glicz.airflow.api.event.bus.ServerEventBus;
import me.glicz.airflow.api.plugin.PluginsLoader;
import me.glicz.airflow.api.properties.ServerProperties;
import me.glicz.airflow.api.service.Services;
import me.glicz.airflow.api.util.Version;
import me.glicz.airflow.command.sender.AirRemoteCommandSender;
import me.glicz.airflow.command.sender.AirServerCommandSender;
import me.glicz.airflow.util.AdventureSerializer;
import net.minecraft.server.dedicated.DedicatedServer;
import org.jetbrains.annotations.NotNull;

public class AirServer implements Server {
    public final Airflow airflow;
    public final DedicatedServer minecraftServer;
    public final AdventureSerializer adventureSerializer;
    private final ServerCommandSender serverCommandSender;
    private final RemoteCommandSender remoteCommandSender;

    public AirServer(Airflow airflow, DedicatedServer minecraftServer) {
        this.airflow = airflow;
        this.minecraftServer = minecraftServer;

        this.adventureSerializer = new AdventureSerializer(this);
        this.serverCommandSender = new AirServerCommandSender(this);
        this.remoteCommandSender = new AirRemoteCommandSender(this);
    }

    @Override
    public @NotNull String getServerBrandName() {
        return this.minecraftServer.getServerModName();
    }

    @Override
    public @NotNull ServerCommandSender getServerCommandSender() {
        return this.serverCommandSender;
    }

    @Override
    public @NotNull RemoteCommandSender getRemoteCommandSender() {
        return this.remoteCommandSender;
    }

    @Override
    public @NotNull Version getServerVersion() {
        return this.airflow.version;
    }

    @Override
    public @NotNull ServerProperties getServerProperties() {
        return this.airflow.serverProperties;
    }

    @Override
    public @NotNull PluginsLoader getPluginsLoader() {
        return this.airflow.pluginLoader;
    }

    @Override
    public @NotNull ServerEventBus getServerEventBus() {
        return this.airflow.serverEventBus;
    }

    @Override
    public @NotNull Services getServices() {
        return this.airflow.services;
    }
}
