package me.glicz.airflow.api.block;

import me.glicz.airflow.api.block.state.BlockState;
import me.glicz.airflow.api.item.ItemTypeLike;
import me.glicz.airflow.api.item.stack.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface BlockType extends ItemTypeLike {
    @NotNull BlockState createBlockState();

    @Override
    default @NotNull BlockType getBlockType() {
        return this;
    }

    @Override
    default @NotNull ItemStack newItemStack(int amount) {
        return getItemType().newItemStack(amount);
    }
}
