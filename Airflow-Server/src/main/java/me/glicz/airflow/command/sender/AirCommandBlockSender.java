package me.glicz.airflow.command.sender;

import me.glicz.airflow.AirServer;
import me.glicz.airflow.api.command.sender.CommandSender;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.level.BaseCommandBlock;

public class AirCommandBlockSender extends AirCommandSender implements CommandSender {
    public AirCommandBlockSender(AirServer server, BaseCommandBlock commandSource) {
        super(server, commandSource);
    }

    public BaseCommandBlock getCommandBlock() {
        return (BaseCommandBlock) commandSource;
    }

    @Override
    public CommandSourceStack createCommandSourceStack() {
        return getCommandBlock().createCommandSourceStack();
    }
}
