package me.glicz.airflow.api.util.math;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public record Vector3d(double x, double y, double z) {
    public static final Vector3d ZERO = new Vector3d(0, 0, 0);

    public Vector3d(@NotNull Vector3i vector) {
        this(vector.x(), vector.y(), vector.z());
    }

    @Contract("_ -> new")
    public @NotNull Vector3d add(@NotNull Vector3d vector) {
        return add(vector.x(), vector.y(), vector.z());
    }

    @Contract("_, _, _ -> new")
    public @NotNull Vector3d add(double x, double y, double z) {
        return new Vector3d(x() + x, y() + y, z() + z);
    }

    @Contract("_ -> new")
    public @NotNull Vector3d subtract(@NotNull Vector3d vector) {
        return subtract(vector.x(), vector.y(), vector.z());
    }

    @Contract("_, _, _ -> new")
    public @NotNull Vector3d subtract(double x, double y, double z) {
        return new Vector3d(x() - x, y() - y, z() - z);
    }

    @Contract("_ -> new")
    public @NotNull Vector3d multiply(double scalar) {
        return multiply(scalar, scalar, scalar);
    }

    @Contract("_ -> new")
    public @NotNull Vector3d multiply(@NotNull Vector3d vector) {
        return multiply(vector.x(), vector.y(), vector.z());
    }

    @Contract("_, _, _ -> new")
    public @NotNull Vector3d multiply(double x, double y, double z) {
        return new Vector3d(x() * x, y() * y, z() * z);
    }

    @Contract("_ -> new")
    public @NotNull Vector3d divide(double scalar) {
        return divide(scalar, scalar, scalar);
    }

    @Contract("_ -> new")
    public @NotNull Vector3d divide(@NotNull Vector3d vector) {
        return divide(vector.x(), vector.y(), vector.z());
    }

    @Contract("_, _, _ -> new")
    public @NotNull Vector3d divide(double x, double y, double z) {
        return new Vector3d(x() / x, y() / y, z() / z);
    }

    public double lengthSquared() {
        return x() * x() + y() * y() + z() * z();
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public double distanceSquared(@NotNull Vector3d vector) {
        return distanceSquared(vector.x(), vector.y(), vector.z());
    }

    public double distanceSquared(double x, double y, double z) {
        double dx = x() - x;
        double dy = y() - y;
        double dz = z() - z;
        return dx * dx + dy * dy + dz * dz;
    }

    public double distance(@NotNull Vector3d vector) {
        return distance(vector.x(), vector.y(), vector.z());
    }

    public double distance(double x, double y, double z) {
        return Math.sqrt(distanceSquared(x, y, z));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector3d(double x1, double y1, double z1))) return false;

        return x() == x1 && y() == y1 && z() == z1;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x(), y(), z());
    }
}
