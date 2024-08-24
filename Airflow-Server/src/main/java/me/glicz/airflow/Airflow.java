package me.glicz.airflow;

import me.glicz.airflow.api.properties.ServerProperties;
import me.glicz.airflow.api.util.Version;
import me.glicz.airflow.properties.AirServerProperties;
import me.glicz.airflow.util.AirVersion;
import net.minecraft.SharedConstants;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.dedicated.DedicatedServerSettings;

import java.util.concurrent.atomic.AtomicReference;

public class Airflow {
    public final ServerProperties serverProperties;
    public final Version version;
    private final AtomicReference<AirServer> server;

    public Airflow(DedicatedServerSettings settings) {
        this.server = new AtomicReference<>();
        this.serverProperties = new AirServerProperties(settings);
        this.version = new AirVersion(SharedConstants.getCurrentVersion());
    }

    public AirServer getServer() {
        return server.get();
    }

    public void createServer(DedicatedServer minecraftServer) {
        if (!this.server.compareAndSet(null, new AirServer(this, minecraftServer))) {
            throw new IllegalStateException("Server already exists");
        }
    }
}
