package me.glicz.airflow.api.util.math.position;

record FinePositionImpl(double x, double y, double z) implements FinePosition {
    @Override
    public int blockX() {
        return (int) x;
    }

    @Override
    public int blockY() {
        return (int) y;
    }

    @Override
    public int blockZ() {
        return (int) z;
    }
}
