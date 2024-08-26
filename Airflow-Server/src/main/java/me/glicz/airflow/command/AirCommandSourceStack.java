package me.glicz.airflow.command;

import me.glicz.airflow.api.command.CommandSourceStack;
import me.glicz.airflow.api.command.sender.CommandSender;
import me.glicz.airflow.api.entity.Entity;

import java.util.Optional;

public interface AirCommandSourceStack extends CommandSourceStack {
    net.minecraft.commands.CommandSourceStack getHandle();

    @Override
    default CommandSender getSender() {
        return getHandle().source.getAirCommandSender();
    }

    @Override
    default Entity getExecutor() {
        return Optional.ofNullable(getHandle().getEntity())
                .map(net.minecraft.world.entity.Entity::getAirEntity)
                .orElse(null);
    }
}
