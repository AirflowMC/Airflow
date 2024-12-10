package me.glicz.airflow.util;

import me.glicz.airflow.Airflow;
import me.glicz.airflow.api.util.Version;
import net.minecraft.WorldVersion;

import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Manifest;

public class AirVersion implements Version {
    private final WorldVersion worldVersion;
    private final String branch, commit;

    public AirVersion(WorldVersion worldVersion) {
        this.worldVersion = worldVersion;

        try (InputStream is = Airflow.class.getClassLoader().getResourceAsStream("META-INF/MANIFEST.MF")) {
            Manifest manifest = new Manifest(is);

            this.branch = manifest.getMainAttributes().getValue("Git-Branch");
            this.commit = manifest.getMainAttributes().getValue("Git-Commit");
        } catch (IOException e) {
            throw new RuntimeException("Failed to retrieve build data", e);
        }
    }

    @Override
    public String getName() {
        return worldVersion.getName();
    }

    @Override
    public int getProtocolVersion() {
        return worldVersion.getProtocolVersion();
    }

    @Override
    public boolean isStable() {
        return worldVersion.isStable();
    }

    @Override
    public String getBranch() {
        return branch;
    }

    @Override
    public String getCommit() {
        return commit;
    }
}
