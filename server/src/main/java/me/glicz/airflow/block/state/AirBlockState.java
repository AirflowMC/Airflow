package me.glicz.airflow.block.state;

import me.glicz.airflow.api.block.BlockType;
import me.glicz.airflow.api.block.state.BlockState;
import me.glicz.airflow.api.block.state.BlockStateProperty;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.NotNull;

public class AirBlockState implements BlockState {
    public final net.minecraft.world.level.block.state.BlockState handle;

    public AirBlockState(net.minecraft.world.level.block.state.BlockState handle) {
        this.handle = handle;
    }

    @Override
    public BlockType getType() {
        return handle.getBlock().airBlockType;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public <T extends Comparable<T>> @NotNull T getProperty(@NotNull BlockStateProperty<T> property) {
        return (T) handle.getValue((Property) ((AirBlockStateProperty<T>) property).getHandle());
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public <T extends Comparable<T>> @NotNull BlockState withProperty(@NotNull BlockStateProperty<T> property, @NotNull T value) {
        return handle.setValue((Property) ((AirBlockStateProperty<T>) property).getHandle(), value).airBlockState;
    }

    @Override
    public String toString() {
        return BlockStateParser.serialize(handle);
    }
}
