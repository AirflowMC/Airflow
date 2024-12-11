package me.glicz.airflow.api;

import me.glicz.airflow.api.event.bus.ServerEventBus;
import me.glicz.airflow.api.permission.Permissions;
import me.glicz.airflow.api.plugin.PluginsLoader;
import me.glicz.airflow.api.properties.ServerProperties;
import me.glicz.airflow.api.service.Services;
import me.glicz.airflow.api.util.Version;
import org.jetbrains.annotations.NotNull;

public interface ServerInfoProvider {
    @NotNull Version getServerVersion();

    @NotNull ServerProperties getServerProperties();

    @NotNull PluginsLoader getPluginsLoader();

    @NotNull ServerEventBus getServerEventBus();

    @NotNull Permissions getPermissions();

    @NotNull Services getServices();
}
