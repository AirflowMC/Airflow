package me.glicz.airflow.api.util.math.position;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public sealed interface Position permits BlockPosition, FinePosition {
    @Contract("_, _, _ -> new")
    static @NotNull BlockPosition block(int x, int y, int z) {
        return new BlockPositionImpl(x, y, z);
    }

    @Contract("_, _, _ -> new")
    static @NotNull FinePosition fine(double x, double y, double z) {
        return new FinePositionImpl(x, y, z);
    }

    int blockX();

    int blockY();

    int blockZ();

    double x();

    double y();

    double z();

    Position add(Position position);

    Position add(double x, double y, double z);

    Position subtract(Position position);

    Position subtract(double x, double y, double z);

    Position scale(double factor);

    Position reverse();

    Position multiply(Position position);

    Position multiply(double x, double y, double z);

    default BlockPosition asBlock() {
        return block(blockX(), blockY(), blockZ());
    }

    default FinePosition asFine() {
        return fine(x(), y(), z());
    }

    default FinePosition toCenter() {
        return fine(blockX() + 0.5, blockY() + 0.5, blockZ() + 0.5);
    }

    default FinePosition toBottomCenter() {
        return fine(blockX() + 0.5, blockY(), blockZ() + 0.5);
    }
}
