package me.glicz.airflow.api.world;

import me.glicz.airflow.api.util.math.Vector2f;
import me.glicz.airflow.api.util.math.Vector3d;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

import java.lang.ref.WeakReference;
import java.util.Objects;

public class Location {
    private final WeakReference<World> world;
    private final Vector3d position;
    private final Vector2f rotation;

    public Location(double x, double y, double z) {
        this(new Vector3d(x, y, z));
    }

    public Location(@NotNull Vector3d position) {
        this(null, position);
    }

    public Location(double x, double y, double z, float pitch, float yaw) {
        this(new Vector3d(x, y, z), new Vector2f(pitch, yaw));
    }

    public Location(@NotNull Vector3d position, @NotNull Vector2f rotation) {
        this(null, position, rotation);
    }

    public Location(@Nullable World world, double x, double y, double z) {
        this(world, new Vector3d(x, y, z));
    }

    public Location(@Nullable World world, @NotNull Vector3d position) {
        this(world, position, Vector2f.ZERO);
    }

    public Location(@Nullable World world, double x, double y, double z, float pitch, float yaw) {
        this(world, new Vector3d(x, y, z), new Vector2f(pitch, yaw));
    }

    public Location(@Nullable World world, @NotNull Vector3d position, @NotNull Vector2f rotation) {
        this.world = new WeakReference<>(world);
        this.position = position;
        this.rotation = rotation;
    }

    public @UnknownNullability World getWorld() {
        return world.get();
    }

    public @NotNull Vector3d getPosition() {
        return position;
    }

    public @NotNull Vector2f getRotation() {
        return rotation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location location)) return false;

        return Objects.equals(world.get(), location.world.get()) && Objects.equals(position, location.position) && Objects.equals(rotation, location.rotation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(world.get(), position, rotation);
    }
}
