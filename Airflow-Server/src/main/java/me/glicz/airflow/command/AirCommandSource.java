package me.glicz.airflow.command;

import me.glicz.airflow.AirServer;
import me.glicz.airflow.api.Server;
import me.glicz.airflow.api.command.CommandSource;
import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public class AirCommandSource implements CommandSource {
    public final AirServer server;
    public final net.minecraft.commands.CommandSource commandSource;

    public AirCommandSource(AirServer server, net.minecraft.commands.CommandSource commandSource) {
        this.server = server;
        this.commandSource = commandSource;
    }

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
}
