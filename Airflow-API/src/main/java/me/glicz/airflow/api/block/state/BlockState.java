package me.glicz.airflow.api.block.state;

import me.glicz.airflow.api.block.BlockType;
import me.glicz.airflow.api.util.Typed;
import org.jetbrains.annotations.NotNull;

public interface BlockState extends Typed<BlockType> {
    @NotNull <T extends Comparable<T>> T getProperty(@NotNull BlockStateProperty<T> property);

    @NotNull <T extends Comparable<T>> BlockState withProperty(@NotNull BlockStateProperty<T> property, @NotNull T value);
}
