package me.glicz.airflow.api.inventory;

import me.glicz.airflow.api.item.stack.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Inventory {
    int getSize();

    @NotNull ItemStack getItem(int slot);

    @NotNull List<ItemStack> getItems();

    void setItems(@NotNull List<ItemStack> items);

    void setItem(int slot, @NotNull ItemStack item);

    int getFirstEmptySlot();

    boolean addItem(@NotNull ItemStack item);

    boolean removeItem(@NotNull ItemStack item);

    boolean removeItemExact(@NotNull ItemStack item);

    void clear();
}
