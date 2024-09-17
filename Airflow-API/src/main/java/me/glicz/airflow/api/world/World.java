package me.glicz.airflow.api.world;

import me.glicz.airflow.api.ServerAware;
import me.glicz.airflow.api.block.Block;
import me.glicz.airflow.api.util.math.Vector3i;
import org.jetbrains.annotations.NotNull;

public interface World extends ServerAware {
    @NotNull String getName();

    default @NotNull Block getBlockAt(Vector3i position) {
        return getBlockAt(position.x(), position.y(), position.z());
    }

    @NotNull Block getBlockAt(int x, int y, int z);
}
