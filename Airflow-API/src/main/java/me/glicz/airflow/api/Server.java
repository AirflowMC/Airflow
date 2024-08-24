package me.glicz.airflow.api;

import me.glicz.airflow.api.command.ServerCommandSource;
import me.glicz.airflow.api.util.Version;

public interface Server {
    Version getVersion();

    String getServerBrandName();

    ServerCommandSource getServerCommandSource();
}
