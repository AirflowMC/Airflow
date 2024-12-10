package me.glicz.airflow.block;

import me.glicz.airflow.api.block.Block;
import me.glicz.airflow.api.block.BlockType;
import me.glicz.airflow.api.block.state.BlockState;
import me.glicz.airflow.api.util.math.Vector3i;
import me.glicz.airflow.api.world.World;
import me.glicz.airflow.block.state.AirBlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.NotNull;

public class AirBlock implements Block {
    public final ServerLevel level;
    public final Vector3i position;

    public AirBlock(ServerLevel level, Vector3i position) {
        this.level = level;
        this.position = position;
    }

    public BlockPos getBlockPos() {
        return new BlockPos(position.x(), position.y(), position.z());
    }

    @Override
    public @NotNull World getWorld() {
        return level.airWorld;
    }

    @Override
    public @NotNull Vector3i getPosition() {
        return position;
    }

    @Override
    public @NotNull BlockState getState() {
        return level.getBlockState(getBlockPos()).airBlockState;
    }

    @Override
    public void setState(@NotNull BlockState state) {
        level.setBlockAndUpdate(getBlockPos(), ((AirBlockState) state).handle);
    }

    @Override
    public BlockType getType() {
        return getState().getType();
    }
}
