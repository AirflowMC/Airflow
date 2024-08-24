package me.glicz.airflow.api.properties;

import org.jetbrains.annotations.NotNull;

public interface ServerProperties {
    boolean isOnlineMode();

    @NotNull String getServerIp();

    int getServerPort();
}
