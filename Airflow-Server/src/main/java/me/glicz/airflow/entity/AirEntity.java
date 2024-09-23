package me.glicz.airflow.entity;

import me.glicz.airflow.api.block.Block;
import me.glicz.airflow.api.command.CommandSourceStack;
import me.glicz.airflow.api.entity.EntityType;
import me.glicz.airflow.api.util.math.Vector2f;
import me.glicz.airflow.api.util.math.Vector3d;
import me.glicz.airflow.api.world.World;
import me.glicz.airflow.command.sender.AirCommandSender;
import net.kyori.adventure.text.Component;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
    public @NotNull World getWorld() {
        //noinspection resource
        return ((ServerLevel) getHandle().level()).airWorld;
    }

    @Override
    public @NotNull Vector3d getPosition() {
        return new Vector3d(getHandle().getX(), getHandle().getY(), getHandle().getZ());
    }

    @Override
    public @NotNull Vector2f getRotation() {
        return new Vector2f(getHandle().getXRot(), getHandle().getYRot());
    }

    @Override
    public @NotNull Vector3d getVelocity() {
        Vec3 velocity = getHandle().getDeltaMovement();
        return new Vector3d(velocity.x, velocity.y, velocity.y);
    }

    @Override
    public void setVelocity(@NotNull Vector3d velocity) {
        getHandle().setDeltaMovement(velocity.x(), velocity.y(), velocity.z());
        getHandle().hurtMarked = true;
    }

    @Override
    public @Nullable Component getCustomName() {
        return componentSerializer().deserializeOrNull(getHandle().getCustomName());
    }

    @Override
    public void setCustomName(@Nullable Component name) {
        getHandle().setCustomName(componentSerializer().serializeOrNull(name));
    }

    @Override
    public boolean isCustomNameVisible() {
        return getHandle().isCustomNameVisible();
    }

    @Override
    public void setCustomNameVisible(boolean visible) {
        getHandle().setCustomNameVisible(visible);
    }

    @Override
    public boolean isAlive() {
        return getHandle().isAlive();
    }

    @Override
    public @NotNull Block getBlockBelow() {
        BlockPos pos = getHandle().getOnPos();
        return getWorld().getBlockAt(pos.getX(), pos.getY(), pos.getZ());
    }

    @Override
    public @NotNull Block getMovementAffectingBlock() {
        BlockPos pos = getHandle().getBlockPosBelowThatAffectsMyMovement();
        return getWorld().getBlockAt(pos.getX(), pos.getY(), pos.getZ());
    }

    @Override
    public @NotNull String getName() {
        return getHandle().getName().getString();
    }

    @Override
    public @NotNull Component getDisplayName() {
        return componentSerializer().deserialize(getHandle().getDisplayName());
    }

    @Override
    public boolean isOperator() {
        return false;
    }

    @Override
    public @NotNull CommandSourceStack createCommandSourceStack() {
        return getHandle().createCommandSourceStack();
    }

    @Override
    public EntityType<?> getType() {
        return getHandle().getType().airEntityType;
    }
}
