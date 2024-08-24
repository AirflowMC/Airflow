package me.glicz.airflow;

import me.glicz.airflow.api.Server;
import me.glicz.airflow.api.command.ServerCommandSource;
import me.glicz.airflow.api.util.Version;
import me.glicz.airflow.command.AirServerCommandSource;
import me.glicz.airflow.util.AdventureSerializer;
import me.glicz.airflow.util.AirVersion;
import net.minecraft.SharedConstants;
import net.minecraft.server.MinecraftServer;

public class AirServer implements Server {
    public final MinecraftServer minecraftServer;
    public final AdventureSerializer adventureSerializer;
    private final Version version;
    private final ServerCommandSource serverCommandSource;

    public AirServer(MinecraftServer minecraftServer) {
        this.minecraftServer = minecraftServer;
        this.adventureSerializer = new AdventureSerializer(this);
        this.version = new AirVersion(SharedConstants.getCurrentVersion());
        this.serverCommandSource = new AirServerCommandSource(this);
    }

    @Override
    public Version getVersion() {
        return this.version;
    }

    @Override
    public String getServerBrandName() {
        return this.minecraftServer.getServerModName();
    }

    @Override
    public ServerCommandSource getServerCommandSource() {
        return this.serverCommandSource;
    }
}
