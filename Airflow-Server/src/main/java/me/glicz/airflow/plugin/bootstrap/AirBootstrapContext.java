package me.glicz.airflow.plugin.bootstrap;

import me.glicz.airflow.Airflow;
import me.glicz.airflow.api.plugin.PluginsLoader;
import me.glicz.airflow.api.plugin.bootstrap.BootstrapContext;
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

    @Override
    public @NotNull PluginsLoader getPluginsLoader() {
        return this.airflow.pluginLoader;
    }
}
