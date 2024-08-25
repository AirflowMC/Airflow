package me.glicz.airflow.command;

import me.glicz.airflow.AirServer;
import me.glicz.airflow.api.command.ServerCommandSender;
import net.minecraft.commands.CommandSourceStack;

public class AirServerCommandSender extends AirCommandSender implements ServerCommandSender {
    public AirServerCommandSender(AirServer server) {
        super(server, server.minecraftServer);
    }

    @Override
    public CommandSourceStack createCommandSourceStack() {
        return this.server.minecraftServer.createCommandSourceStack();
    }
}
