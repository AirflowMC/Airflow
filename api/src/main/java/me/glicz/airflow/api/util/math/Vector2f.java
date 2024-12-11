package me.glicz.airflow.api.util.math;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public record Vector2f(float x, float y) {
    public static final Vector2f ZERO = new Vector2f(0, 0);

    @Contract("_ -> new")
    public @NotNull Vector2f add(@NotNull Vector2f vector) {
        return add(vector.x(), vector.y());
    }

    @Contract("_, _ -> new")
    public @NotNull Vector2f add(float x, float y) {
        return new Vector2f(x() + x, y() + y);
    }

    @Contract("_ -> new")
    public @NotNull Vector2f subtract(@NotNull Vector2f vector) {
        return subtract(vector.x(), vector.y());
    }

    @Contract("_, _ -> new")
    public @NotNull Vector2f subtract(float x, float y) {
        return new Vector2f(x() - x, y() - y);
    }

    @Contract("_ -> new")
    public @NotNull Vector2f multiply(float scalar) {
        return new Vector2f(x() * scalar, y() * scalar);
    }

    @Contract("_ -> new")
    public @NotNull Vector2f multiply(@NotNull Vector2f vector) {
        return multiply(vector.x(), vector.y());
    }

    @Contract("_, _ -> new")
    public @NotNull Vector2f multiply(float x, float y) {
        return new Vector2f(x() * x, y() * y);
    }

    @Contract("_ -> new")
    public @NotNull Vector2f divide(float scalar) {
        if (scalar == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        return new Vector2f(x() / scalar, y() / scalar);
    }

    @Contract("_ -> new")
    public @NotNull Vector2f divide(@NotNull Vector2f vector) {
        return divide(vector.x(), vector.y());
    }

    @Contract("_, _ -> new")
    public @NotNull Vector2f divide(float x, float y) {
        return new Vector2f(x() / x, y() / y);
    }

    public float lengthSquared() {
        return x() * x() + y() * y();
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public float distanceSquared(@NotNull Vector2f vector) {
        return distanceSquared(vector.x(), vector.y());
    }

    public float distanceSquared(float x, float y) {
        float dx = x() - x;
        float dy = y() - y;
        return dx * dx + dy * dy;
    }

    public double distance(@NotNull Vector2f vector) {
        return distance(vector.x(), vector.y());
    }

    public double distance(float x, float y) {
        return Math.sqrt(distanceSquared(x, y));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector2f(float x1, float y1))) return false;

        return x() == x1 && y() == y1;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x(), y());
    }
}
