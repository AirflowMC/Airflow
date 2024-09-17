package me.glicz.airflow.command;

import me.glicz.airflow.api.command.CommandSourceStack;
import me.glicz.airflow.api.command.sender.CommandSender;
import me.glicz.airflow.api.entity.Entity;
import me.glicz.airflow.api.util.math.Vector2f;
import me.glicz.airflow.api.util.math.Vector3d;
import me.glicz.airflow.api.world.Location;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface AirCommandSourceStack extends CommandSourceStack {
    net.minecraft.commands.CommandSourceStack getHandle();

    @Override
    default @NotNull CommandSender getSender() {
        return getHandle().source.getAirCommandSender();
    }

    @Override
    default Entity getExecutor() {
        return Optional.ofNullable(getHandle().getEntity())
                .map(net.minecraft.world.entity.Entity::getAirEntity)
                .orElse(null);
    }

    @Override
    default @NotNull Location getLocation() {
        Vec3 pos = getHandle().getPosition();
        Vec2 rot = getHandle().getRotation();

        return new Location(
                getHandle().getLevel().airWorld,
                new Vector3d(pos.x, pos.y, pos.z),
                new Vector2f(rot.x, rot.y)
        );
    }
}
