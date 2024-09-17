package me.glicz.airflow.entity;

import me.glicz.airflow.api.entity.EntityType;
import me.glicz.airflow.api.util.math.Vector2f;
import me.glicz.airflow.api.util.math.Vector3d;
import me.glicz.airflow.api.world.Location;
import me.glicz.airflow.command.sender.AirCommandSender;
import me.glicz.airflow.util.MinecraftComponentSerializer;
import net.kyori.adventure.text.Component;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class AirEntity extends AirCommandSender implements me.glicz.airflow.api.entity.Entity {
    public AirEntity(Entity handle) {
        super(handle.getServer().getDedicatedServer().airflow.getServer(), handle);
    }

    public Entity getHandle() {
        return (Entity) commandSource;
    }

    @Override
    public @NotNull UUID getUniqueId() {
        return getHandle().getUUID();
    }

    @Override
    public @NotNull Location getLocation() {
        //noinspection resource
        return new Location(
                ((ServerLevel) getHandle().level()).airWorld,
                new Vector3d(getHandle().getX(), getHandle().getY(), getHandle().getZ()),
                new Vector2f(getHandle().getXRot(), getHandle().getYRot())
        );
    }

    @Override
    public boolean isAlive() {
        return getHandle().isAlive();
    }

    @Override
    public CommandSourceStack createCommandSourceStack() {
        return getHandle().createCommandSourceStack();
    }

    @Override
    public MinecraftComponentSerializer componentSerializer() {
        return new MinecraftComponentSerializer(() -> getHandle().registryAccess());
    }

    @Override
    public String getName() {
        return getHandle().getName().getString();
    }

    @Override
    public Component getDisplayName() {
        return componentSerializer().deserialize(getHandle().getDisplayName());
    }

    @Override
    public boolean isOperator() {
        return false;
    }

    @Override
    public EntityType<?> getType() {
        return getHandle().getType().airEntityType;
    }
}
