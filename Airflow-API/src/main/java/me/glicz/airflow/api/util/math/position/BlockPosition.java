package me.glicz.airflow.api.util.math.position;

public sealed interface BlockPosition extends Position permits BlockPositionImpl {
    @Override
    default BlockPosition add(Position position) {
        return add(position.x(), position.y(), position.z());
    }

    @Override
    default BlockPosition add(double x, double y, double z) {
        return Position.block((int) (x() + x), (int) (y() + y), (int) (z() + z));
    }

    @Override
    default BlockPosition subtract(Position position) {
        return subtract(position.x(), position.y(), position.z());
    }

    @Override
    default BlockPosition subtract(double x, double y, double z) {
        return add(-x, -y, -z);
    }

    @Override
    default BlockPosition scale(double factor) {
        return multiply(factor, factor, factor);
    }

    @Override
    default BlockPosition reverse() {
        return scale(-1);
    }

    @Override
    default BlockPosition multiply(Position position) {
        return multiply(position.x(), position.y(), position.z());
    }

    @Override
    default BlockPosition multiply(double x, double y, double z) {
        return Position.block((int) (x() * x), (int) (y() * y), (int) (z() * z));
    }
}
