package me.glicz.airflow.api.block;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.glicz.airflow.api.block.state.BlockState;
import me.glicz.airflow.api.item.ItemTypeLike;
import me.glicz.airflow.api.item.stack.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface BlockType extends ItemTypeLike {
    @NotNull BlockState createBlockState();

    @NotNull BlockState createBlockState(String state) throws CommandSyntaxException;

    @Override
    default @NotNull BlockType asBlockType() {
        return this;
    }

    @Override
    default @NotNull ItemStack newItemStack(int amount) {
        return asItemType().newItemStack(amount);
    }
}
