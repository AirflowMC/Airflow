package me.glicz.airflow.api.util.math.rotation;

record RotationImpl(float yaw, float pitch) implements Rotation {
    @Override
    public Rotation add(Rotation rotation) {
        return add(rotation.yaw(), rotation.pitch());
    }

    @Override
    public Rotation add(float yaw, float pitch) {
        return Rotation.rotation(yaw() + yaw, pitch() + pitch);
    }

    @Override
    public Rotation subtract(Rotation rotation) {
        return subtract(rotation.yaw(), rotation.pitch());
    }

    @Override
    public Rotation subtract(float yaw, float pitch) {
        return add(-yaw, -pitch);
    }

    @Override
    public Rotation scale(float factor) {
        return multiply(factor, factor);
    }

    @Override
    public Rotation reverse() {
        return scale(-1);
    }

    @Override
    public Rotation multiply(Rotation rotation) {
        return multiply(rotation.yaw(), rotation.pitch());
    }

    @Override
    public Rotation multiply(float yaw, float pitch) {
        return Rotation.rotation(yaw() * yaw, pitch() * pitch);
    }
}
