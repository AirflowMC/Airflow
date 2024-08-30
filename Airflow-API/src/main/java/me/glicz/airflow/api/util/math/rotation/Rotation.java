package me.glicz.airflow.api.util.math.rotation;

public interface Rotation {
    static Rotation rotation(float yaw, float pitch) {
        return new RotationImpl(yaw, pitch);
    }

    float yaw();

    float pitch();

    Rotation add(Rotation rotation);

    Rotation add(float yaw, float pitch);

    Rotation subtract(Rotation rotation);

    Rotation subtract(float yaw, float pitch);

    Rotation scale(float factor);

    Rotation reverse();

    Rotation multiply(Rotation rotation);

    Rotation multiply(float yaw, float pitch);
}
