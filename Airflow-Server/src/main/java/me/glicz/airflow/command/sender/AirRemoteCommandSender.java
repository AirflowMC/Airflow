package me.glicz.airflow.command.sender;

import me.glicz.airflow.AirServer;
import me.glicz.airflow.api.command.sender.RemoteCommandSender;
import net.minecraft.commands.CommandSourceStack;

public class AirRemoteCommandSender extends AirCommandSender implements RemoteCommandSender {
    public AirRemoteCommandSender(AirServer server) {
        super(server, server.minecraftServer.rconConsoleSource);
    }

    @Override
    public CommandSourceStack createCommandSourceStack() {
        return this.server.minecraftServer.rconConsoleSource.createCommandSourceStack();
    }
}
