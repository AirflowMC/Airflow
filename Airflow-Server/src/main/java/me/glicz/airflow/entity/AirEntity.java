package me.glicz.airflow.entity;

import me.glicz.airflow.AirServer;
import me.glicz.airflow.api.entity.EntityType;
import me.glicz.airflow.command.sender.AirCommandSender;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.Entity;

public class AirEntity extends AirCommandSender implements me.glicz.airflow.api.entity.Entity {
    public AirEntity(AirServer server, Entity handle) {
        super(server, handle);
    }

    public Entity getHandle() {
        return (Entity) commandSource;
    }

    @Override
    public CommandSourceStack createCommandSourceStack() {
        return getHandle().createCommandSourceStack();
    }

    @Override
    public EntityType getType() {
        return getHandle().getType().airEntityType;
    }
}
