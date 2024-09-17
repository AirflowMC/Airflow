package me.glicz.airflow.api.util.math;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public record Vector3i(int x, int y, int z) {
    public static final Vector3i ZERO = new Vector3i(0, 0, 0);

    public Vector3i(@NotNull Vector3d vector) {
        this((int) vector.x(), (int) vector.y(), (int) vector.z());
    }

    @Contract("_ -> new")
    public @NotNull Vector3i add(@NotNull Vector3i vector) {
        return add(vector.x(), vector.y(), vector.z());
    }

    @Contract("_, _, _ -> new")
    public @NotNull Vector3i add(int x, int y, int z) {
        return new Vector3i(x() + x, y() + y, z() + z);
    }

    @Contract("_ -> new")
    public @NotNull Vector3i subtract(@NotNull Vector3i vector) {
        return subtract(vector.x(), vector.y(), vector.z());
    }

    @Contract("_, _, _ -> new")
    public @NotNull Vector3i subtract(int x, int y, int z) {
        return new Vector3i(x() - x, y() - y, z() - z);
    }

    @Contract("_ -> new")
    public @NotNull Vector3i multiply(int scalar) {
        return new Vector3i(x() * scalar, y() * scalar, z() * scalar);
    }

    @Contract("_ -> new")
    public @NotNull Vector3i multiply(@NotNull Vector3i vector) {
        return multiply(vector.x(), vector.y(), vector.z());
    }

    @Contract("_, _, _ -> new")
    public @NotNull Vector3i multiply(int x, int y, int z) {
        return new Vector3i(x() * x, y() * y, z() * z);
    }

    @Contract("_ -> new")
    public @NotNull Vector3i divide(int scalar) {
        if (scalar == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        return new Vector3i(x() / scalar, y() / scalar, z() / scalar);
    }

    @Contract("_ -> new")
    public @NotNull Vector3i divide(@NotNull Vector3i vector) {
        return divide(vector.x(), vector.y(), vector.z());
    }

    @Contract("_, _, _ -> new")
    public @NotNull Vector3i divide(int x, int y, int z) {
        return new Vector3i(x() / x, y() / y, z() / z);
    }

    public int lengthSquared() {
        return x() * x() + y() * y() + z() * z();
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public double distanceSquared(@NotNull Vector3i vector) {
        return distanceSquared(vector.x(), vector.y(), vector.z());
    }

    public double distanceSquared(int x, int y, int z) {
        int dx = x() - x;
        int dy = y() - y;
        int dz = z() - z;
        return dx * dx + dy * dy + dz * dz;
    }

    public double distance(@NotNull Vector3i vector) {
        return distance(vector.x(), vector.y(), vector.z());
    }

    public double distance(int x, int y, int z) {
        return Math.sqrt(distanceSquared(x, y, z));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector3i vector)) return false;

        return x() == vector.x() && y() == vector.y() && z() == vector.z();
    }

    @Override
    public int hashCode() {
        return Objects.hash(x(), y(), z());
    }
}
