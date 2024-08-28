package me.glicz.airflow.block.state;

import me.glicz.airflow.api.block.BlockType;
import me.glicz.airflow.api.block.state.BlockState;

public class AirBlockState implements BlockState {
    private final net.minecraft.world.level.block.state.BlockState handle;

    public AirBlockState(net.minecraft.world.level.block.state.BlockState handle) {
        this.handle = handle;
    }

    @Override
    public BlockType getType() {
        return this.handle.getBlock().airBlockType;
    }
}
