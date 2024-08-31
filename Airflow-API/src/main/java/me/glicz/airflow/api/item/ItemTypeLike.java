package me.glicz.airflow.api.item;

import me.glicz.airflow.api.block.BlockType;
import me.glicz.airflow.api.item.stack.ItemStack;
import net.kyori.adventure.key.Keyed;
import net.kyori.adventure.translation.Translatable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ItemTypeLike extends Keyed, Translatable {
    @NotNull ItemType getItemType();

    @Nullable BlockType getBlockType();

    default @NotNull ItemStack asItemStack() {
        return asItemStack(1);
    }

    @NotNull ItemStack asItemStack(int amount);
}
