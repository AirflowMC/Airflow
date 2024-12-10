package me.glicz.airflow.command.sender;

import me.glicz.airflow.AirServer;
import me.glicz.airflow.api.command.CommandSourceStack;
import me.glicz.airflow.api.command.sender.CommandSender;
import net.kyori.adventure.text.Component;
import net.minecraft.world.level.BaseCommandBlock;
import org.jetbrains.annotations.NotNull;

public class AirCommandBlockSender extends AirCommandSender implements CommandSender {
    public AirCommandBlockSender(AirServer server, BaseCommandBlock commandSource) {
        super(server, commandSource);
    }

    public BaseCommandBlock getCommandBlock() {
        return (BaseCommandBlock) commandSource;
    }

    @Override
    public @NotNull CommandSourceStack createCommandSourceStack() {
        return getCommandBlock().createCommandSourceStack();
    }

    @Override
    public @NotNull String getName() {
        return getCommandBlock().getName().getString();
    }

    @Override
    public @NotNull Component getDisplayName() {
        return server.componentSerializer().deserialize(getCommandBlock().getName());
    }

    @Override
    public boolean isOperator() {
        return true;
    }
}
