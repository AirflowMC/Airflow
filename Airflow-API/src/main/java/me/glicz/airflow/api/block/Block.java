package me.glicz.airflow.api.block;

import me.glicz.airflow.api.block.state.BlockState;
import me.glicz.airflow.api.util.Typed;
import me.glicz.airflow.api.util.math.Vector3i;
import me.glicz.airflow.api.world.World;

public interface Block extends Typed<BlockType> {
    World getWorld();

    Vector3i getPosition();

    default void setType(BlockType type) {
        setState(type.createBlockState());
    }

    BlockState getState();

    void setState(BlockState state);
}
