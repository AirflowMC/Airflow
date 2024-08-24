package me.glicz.airflow;

import me.glicz.airflow.api.Server;
import me.glicz.airflow.api.command.ServerCommandSource;
import me.glicz.airflow.api.properties.ServerProperties;
import me.glicz.airflow.api.util.Version;
import me.glicz.airflow.command.AirServerCommandSource;
import me.glicz.airflow.util.AdventureSerializer;
import net.minecraft.server.dedicated.DedicatedServer;
import org.jetbrains.annotations.NotNull;

public class AirServer implements Server {
    public final Airflow airflow;
    public final DedicatedServer minecraftServer;
    public final AdventureSerializer adventureSerializer;
    private final ServerCommandSource serverCommandSource;

    public AirServer(Airflow airflow, DedicatedServer minecraftServer) {
        this.airflow = airflow;
        this.minecraftServer = minecraftServer;

        this.adventureSerializer = new AdventureSerializer(this);
        this.serverCommandSource = new AirServerCommandSource(this);
    }

    @Override
    public @NotNull String getServerBrandName() {
        return this.minecraftServer.getServerModName();
    }

    @Override
    public @NotNull ServerCommandSource getServerCommandSource() {
        return this.serverCommandSource;
    }

    @Override
    public @NotNull Version getServerVersion() {
        return this.airflow.version;
    }

    @Override
    public @NotNull ServerProperties getServerProperties() {
        return this.airflow.serverProperties;
    }
}
