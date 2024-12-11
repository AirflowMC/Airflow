package me.glicz.airflow.properties;

import me.glicz.airflow.api.properties.ServerProperties;
import net.minecraft.server.dedicated.DedicatedServerSettings;
import org.jetbrains.annotations.NotNull;

public class AirServerProperties implements ServerProperties {
    private final DedicatedServerSettings settings;

    public AirServerProperties(DedicatedServerSettings settings) {
        this.settings = settings;
    }

    @Override
    public boolean isOnlineMode() {
        return this.settings.getProperties().onlineMode;
    }

    @Override
    public @NotNull String getServerIp() {
        return this.settings.getProperties().serverIp;
    }

    @Override
    public int getServerPort() {
        return this.settings.getProperties().serverPort;
    }
}
