package me.glicz.airflow.api.item;

import me.glicz.airflow.api.block.BlockType;
import me.glicz.airflow.api.item.stack.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ItemTypeLike {
    @NotNull ItemType getItemType();

    @Nullable BlockType getBlockType();

    default @NotNull ItemStack asItemStack() {
        return asItemStack(1);
    }

    @NotNull ItemStack asItemStack(int amount);
}
