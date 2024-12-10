package me.glicz.airflow.command.sender;

import me.glicz.airflow.AirServer;
import me.glicz.airflow.api.command.sender.ServerCommandSender;
import net.kyori.adventure.text.Component;
import net.minecraft.commands.CommandSourceStack;
import org.jetbrains.annotations.NotNull;

public class AirServerCommandSender extends AirCommandSender implements ServerCommandSender {
    public AirServerCommandSender(AirServer server) {
        super(server, server.minecraftServer);
    }

    @Override
    public @NotNull CommandSourceStack createCommandSourceStack() {
        return server.minecraftServer.createCommandSourceStack();
    }

    @Override
    public @NotNull String getName() {
        return "Server";
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
