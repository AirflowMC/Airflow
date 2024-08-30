package me.glicz.airflow.block;

import me.glicz.airflow.api.block.Block;
import me.glicz.airflow.api.block.BlockType;
import me.glicz.airflow.api.block.state.BlockState;
import me.glicz.airflow.api.util.math.position.BlockPosition;
import me.glicz.airflow.api.world.World;
import me.glicz.airflow.block.state.AirBlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

public class AirBlock implements Block {
    public final ServerLevel level;
    public final BlockPosition position;

    public AirBlock(ServerLevel level, BlockPosition position) {
        this.level = level;
        this.position = position;
    }

    public BlockPos getBlockPos() {
        return new BlockPos(position.blockX(), position.blockY(), position.blockZ());
    }

    @Override
    public World getWorld() {
        return this.level.airWorld;
    }

    @Override
    public BlockPosition getPosition() {
        return this.position;
    }

    @Override
    public BlockState getState() {
        return this.level.getBlockState(getBlockPos()).getAirBlockState();
    }

    @Override
    public void setState(BlockState state) {
        this.level.setBlockAndUpdate(getBlockPos(), ((AirBlockState) state).handle);
    }

    @Override
    public BlockType getType() {
        return getState().getType();
    }
}
