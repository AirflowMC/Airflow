package me.glicz.airflow.api.util.math.position;

public sealed interface FinePosition extends Position permits FinePositionImpl {
    @Override
    default FinePosition add(Position position) {
        return add(position.x(), position.y(), position.z());
    }

    @Override
    default FinePosition add(double x, double y, double z) {
        return Position.fine(x() + x, y() + y, z() + z);
    }

    @Override
    default FinePosition subtract(Position position) {
        return subtract(position.x(), position.y(), position.z());
    }

    @Override
    default FinePosition subtract(double x, double y, double z) {
        return add(-x, -y, -z);
    }

    @Override
    default FinePosition scale(double factor) {
        return multiply(factor, factor, factor);
    }

    @Override
    default FinePosition reverse() {
        return scale(-1);
    }

    @Override
    default FinePosition multiply(Position position) {
        return multiply(position.x(), position.y(), position.z());
    }

    @Override
    default FinePosition multiply(double x, double y, double z) {
        return Position.fine(x() * x, y() * y, z() * z);
    }
}
