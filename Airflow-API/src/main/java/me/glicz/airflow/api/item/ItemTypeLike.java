package me.glicz.airflow.api.item;

import me.glicz.airflow.api.block.BlockType;
import me.glicz.airflow.api.item.stack.ItemStack;
import net.kyori.adventure.key.Keyed;
import net.kyori.adventure.translation.Translatable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public interface ItemTypeLike extends Keyed, Translatable {
    @NotNull ItemType asItemType();

    @Nullable BlockType asBlockType();

    default @NotNull ItemStack newItemStack() {
        return newItemStack(1);
    }

    @NotNull ItemStack newItemStack(int amount);

    default @NotNull ItemStack newItemStack(@NotNull Consumer<ItemStack> consumer) {
        return newItemStack(1, consumer);
    }

    default @NotNull ItemStack newItemStack(int amount, @NotNull Consumer<ItemStack> consumer) {
        ItemStack itemStack = newItemStack(amount);
        consumer.accept(itemStack);
        return itemStack;
    }
}
