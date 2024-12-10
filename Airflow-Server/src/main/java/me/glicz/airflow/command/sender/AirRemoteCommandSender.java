package me.glicz.airflow.command.sender;

import me.glicz.airflow.AirServer;
import me.glicz.airflow.api.command.sender.RemoteCommandSender;
import net.kyori.adventure.text.Component;
import net.minecraft.commands.CommandSourceStack;
import org.jetbrains.annotations.NotNull;

public class AirRemoteCommandSender extends AirCommandSender implements RemoteCommandSender {
    public AirRemoteCommandSender(AirServer server) {
        super(server, server.minecraftServer.rconConsoleSource);
    }

    @Override
    public @NotNull CommandSourceStack createCommandSourceStack() {
        return server.minecraftServer.rconConsoleSource.createCommandSourceStack();
    }

    @Override
    public @NotNull String getName() {
        return "Rcon";
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.text(getName());
    }

    @Override
    public boolean isOperator() {
        return true;
    }
}
