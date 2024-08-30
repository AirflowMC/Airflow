package me.glicz.airflow.api.util.math.position;

record BlockPositionImpl(int blockX, int blockY, int blockZ) implements BlockPosition {
    @Override
    public double x() {
        return blockX;
    }

    @Override
    public double y() {
        return blockY;
    }

    @Override
    public double z() {
        return blockZ;
    }
}
