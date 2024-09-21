package me.glicz.airflow.command;

import me.glicz.airflow.api.command.CommandResultCallback;
import me.glicz.airflow.api.command.CommandSourceStack;
import me.glicz.airflow.api.command.sender.CommandSender;
import me.glicz.airflow.api.entity.Entity;
import me.glicz.airflow.api.util.math.Vector2f;
import me.glicz.airflow.api.util.math.Vector3d;
import me.glicz.airflow.api.world.Location;
import me.glicz.airflow.api.world.World;
import me.glicz.airflow.command.sender.AirCommandSender;
import me.glicz.airflow.entity.AirEntity;
import me.glicz.airflow.world.AirWorld;
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

    @Override
    default @NotNull CommandResultCallback getCallback() {
        return getHandle().callback();
    }

    @Override
    default @NotNull CommandSourceStack withSender(@NotNull CommandSender sender) {
        return getHandle().withSource(((AirCommandSender) sender).commandSource);
    }

    @Override
    default @NotNull CommandSourceStack withExecutor(@NotNull Entity executor) {
        return getHandle().withEntity(((AirEntity) executor).getHandle());
    }

    @Override
    default @NotNull CommandSourceStack withPosition(@NotNull Vector3d position) {
        return getHandle().withPosition(new Vec3(position.x(), position.y(), position.z()));
    }

    @Override
    default @NotNull CommandSourceStack withRotation(@NotNull Vector2f rotation) {
        return getHandle().withRotation(new Vec2(rotation.x(), rotation.y()));
    }

    @Override
    default @NotNull CommandSourceStack withWorld(@NotNull World world) {
        return getHandle().withLevel(((AirWorld) world).handle);
    }

    @Override
    default @NotNull CommandSourceStack withCallBack(@NotNull CommandResultCallback callback) {
        return getHandle().withCallback((success, result) -> callback.onResult(result));
    }
}
