package me.glicz.airflow.command;

import me.glicz.airflow.AirServer;
import me.glicz.airflow.api.command.ServerCommandSource;

public class AirServerCommandSource extends AirCommandSource implements ServerCommandSource {
    public AirServerCommandSource(AirServer server) {
        super(server, server.minecraftServer);
    }
}
