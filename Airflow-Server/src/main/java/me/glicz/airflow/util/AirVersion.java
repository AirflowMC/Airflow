package me.glicz.airflow.util;

import me.glicz.airflow.api.util.Version;
import net.minecraft.WorldVersion;

public class AirVersion implements Version {
    private final WorldVersion worldVersion;

    public AirVersion(WorldVersion worldVersion) {
        this.worldVersion = worldVersion;
    }

    @Override
    public String getName() {
        return this.worldVersion.getName();
    }

    @Override
    public int getProtocolVersion() {
        return this.worldVersion.getProtocolVersion();
    }

    @Override
    public boolean isStable() {
        return this.worldVersion.isStable();
    }
}
