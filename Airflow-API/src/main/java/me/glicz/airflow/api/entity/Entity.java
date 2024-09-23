package me.glicz.airflow.api.entity;

import me.glicz.airflow.api.block.Block;
import me.glicz.airflow.api.command.sender.CommandSender;
import me.glicz.airflow.api.util.Typed;
import me.glicz.airflow.api.util.math.Vector2f;
import me.glicz.airflow.api.util.math.Vector3d;
import me.glicz.airflow.api.world.Location;
import me.glicz.airflow.api.world.World;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface Entity extends CommandSender, Typed<EntityType<?>> {
    @NotNull UUID getUniqueId();

    @NotNull World getWorld();

    @NotNull Vector3d getPosition();

    @NotNull Vector2f getRotation();

    default @NotNull Location getLocation() {
        return new Location(getWorld(), getPosition(), getRotation());
    }

    @NotNull Vector3d getVelocity();

    void setVelocity(@NotNull Vector3d velocity);

    default void addVelocity(@NotNull Vector3d velocity) {
        setVelocity(getVelocity().add(velocity));
    }

    @Nullable Component getCustomName();

    void setCustomName(@Nullable Component name);

    boolean isCustomNameVisible();

    void setCustomNameVisible(boolean visible);

    boolean isAlive();

    @NotNull Block getBlockBelow();

    @NotNull Block getMovementAffectingBlock();
}
