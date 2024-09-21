package me.glicz.airflow.api.block.state;

import org.jetbrains.annotations.NotNull;

public interface BlockStateProperty<T> {
    @NotNull String getName();

    interface Boolean extends BlockStateProperty<java.lang.Boolean> {
    }

    interface Integer extends BlockStateProperty<java.lang.Integer> {
        int getMin();

        int getMax();
    }
}
 