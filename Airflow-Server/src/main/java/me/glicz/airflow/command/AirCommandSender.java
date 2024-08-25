package me.glicz.airflow.command;

import me.glicz.airflow.AirServer;
import me.glicz.airflow.api.Server;
import me.glicz.airflow.api.command.CommandSender;
import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import org.jetbrains.annotations.NotNull;

public abstract class AirCommandSender implements CommandSender {
    public final AirServer server;
    public final CommandSource commandSource;

    public AirCommandSender(AirServer server, CommandSource commandSource) {
        this.server = server;
        this.commandSource = commandSource;
    }

    public abstract CommandSourceStack createCommandSourceStack();

    @Override
    public @NotNull Server getServer() {
        return this.server;
    }

    @SuppressWarnings({"UnstableApiUsage", "deprecation"}) // TODO Adventure 5.0.0
    @Override
    public void sendMessage(@NotNull Identity source, @NotNull Component message, @NotNull MessageType type) {
        if (type != MessageType.SYSTEM) return;

        this.commandSource.sendSystemMessage(this.server.adventureSerializer.toMinecraft(message));
    }

    @Override
    public void dispatch(String command) {
        this.server.minecraftServer.getCommands().performPrefixedCommand(createCommandSourceStack(), command);
    }
}
