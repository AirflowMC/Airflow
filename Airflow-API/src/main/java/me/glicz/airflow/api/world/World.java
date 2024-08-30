package me.glicz.airflow.api.world;

import me.glicz.airflow.api.ServerAware;
import me.glicz.airflow.api.block.Block;
import me.glicz.airflow.api.util.math.position.Position;

public interface World extends ServerAware {
    String getName();

    default Block getBlockAt(Position position) {
        return getBlockAt(position.blockX(), position.blockY(), position.blockZ());
    }

    Block getBlockAt(int x, int y, int z);
}
