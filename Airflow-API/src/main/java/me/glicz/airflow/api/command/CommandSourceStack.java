package me.glicz.airflow.api.command;

import me.glicz.airflow.api.command.sender.CommandSender;
import me.glicz.airflow.api.entity.Entity;
import me.glicz.airflow.api.util.math.Vector2f;
import me.glicz.airflow.api.util.math.Vector3d;
import me.glicz.airflow.api.world.Location;
import me.glicz.airflow.api.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public interface CommandSourceStack {
    @NotNull CommandSender getSender();

    @Nullable Entity getExecutor();

    default @NotNull CommandSender getTarget() {
        return Objects.requireNonNullElse(getExecutor(), getSender());
    }

    @NotNull Location getLocation();

    @NotNull CommandResultCallback getCallback();

    @NotNull CommandSourceStack withSender(@NotNull CommandSender sender);

    @NotNull CommandSourceStack withExecutor(@NotNull Entity executor);

    @NotNull CommandSourceStack withPosition(@NotNull Vector3d position);

    @NotNull CommandSourceStack withRotation(@NotNull Vector2f rotation);

    @NotNull CommandSourceStack withWorld(@NotNull World world);

    default @NotNull CommandSourceStack withLocation(@NotNull Location location) {
        return withWorld(location.getWorld()).withPosition(location.getPosition()).withRotation(location.getRotation());
    }

    @NotNull CommandSourceStack withCallBack(@NotNull CommandResultCallback callback);
}
