package me.glicz.airflow.api.entity;

import me.glicz.airflow.api.command.sender.CommandSender;
import me.glicz.airflow.api.util.Typed;
import me.glicz.airflow.api.util.math.position.FinePosition;
import me.glicz.airflow.api.util.math.rotation.Rotation;
import me.glicz.airflow.api.world.World;

import java.util.UUID;

public interface Entity extends CommandSender, Typed<EntityType> {
    UUID getUniqueId();

    World getWorld();

    FinePosition getPosition();

    Rotation getRotation();
}
