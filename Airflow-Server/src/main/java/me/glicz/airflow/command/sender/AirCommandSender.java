package me.glicz.airflow.command.sender;

import me.glicz.airflow.AirServer;
import me.glicz.airflow.api.Server;
import me.glicz.airflow.api.command.sender.CommandSender;
import me.glicz.airflow.util.MinecraftComponentSerializer;
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

    public MinecraftComponentSerializer componentSerializer() {
        return MinecraftComponentSerializer.INSTANCE;
    }

    @Override
    public @NotNull Server getServer() {
        return this.server;
    }

    @SuppressWarnings({"UnstableApiUsage", "deprecation"}) // TODO Adventure 5.0.0
    @Override
    public void sendMessage(@NotNull Identity source, @NotNull Component message, @NotNull MessageType type) {
        if (type != MessageType.SYSTEM) return;

        this.commandSource.sendSystemMessage(componentSerializer().serialize(message));
    }

    @Override
    public String getName() {
        return createCommandSourceStack().getTextName();
    }

    @Override
    public Component getDisplayName() {
        return componentSerializer().deserialize(createCommandSourceStack().getDisplayName());
    }

    @Override
    public void dispatch(String command) {
        this.server.minecraftServer.getCommands().performPrefixedCommand(createCommandSourceStack(), command);
    }
}
