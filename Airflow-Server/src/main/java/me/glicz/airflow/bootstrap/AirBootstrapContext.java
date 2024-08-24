package me.glicz.airflow.bootstrap;

import me.glicz.airflow.Airflow;
import me.glicz.airflow.api.bootstrap.BootstrapContext;
import me.glicz.airflow.api.properties.ServerProperties;
import me.glicz.airflow.api.util.Version;
import org.jetbrains.annotations.NotNull;

public class AirBootstrapContext implements BootstrapContext {
    private final Airflow airflow;

    public AirBootstrapContext(Airflow airflow) {
        this.airflow = airflow;
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
