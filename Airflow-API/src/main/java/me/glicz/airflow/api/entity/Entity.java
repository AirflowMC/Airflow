package me.glicz.airflow.api.entity;

import me.glicz.airflow.api.command.sender.CommandSender;
import me.glicz.airflow.api.util.Typed;
import me.glicz.airflow.api.util.math.Vector2f;
import me.glicz.airflow.api.util.math.Vector3d;
import me.glicz.airflow.api.world.Location;
import me.glicz.airflow.api.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface Entity extends CommandSender, Typed<EntityType<?>> {
    @NotNull UUID getUniqueId();

    @NotNull World getWorld();

    @NotNull Vector3d getPosition();

    @NotNull Vector2f getRotation();

    default @NotNull Location getLocation() {
        return new Location(getWorld(), getPosition(), getRotation());
    }

    boolean isAlive();
}
