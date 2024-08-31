package me.glicz.airflow.api.block;

import me.glicz.airflow.api.block.state.BlockState;
import me.glicz.airflow.api.item.ItemTypeLike;
import me.glicz.airflow.api.item.stack.ItemStack;
import net.kyori.adventure.key.Keyed;
import net.kyori.adventure.translation.Translatable;
import org.jetbrains.annotations.NotNull;

public interface BlockType extends ItemTypeLike, Keyed, Translatable {
    @NotNull BlockState createBlockState();

    @Override
    default @NotNull BlockType getBlockType() {
        return this;
    }

    @Override
    default @NotNull ItemStack asItemStack(int amount) {
        return getItemType().asItemStack(amount);
    }
}
