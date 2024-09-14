package me.glicz.airflow.api.inventory;

import me.glicz.airflow.api.item.stack.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Inventory {
    @NotNull List<ItemStack> getItems();

    void setItems(@NotNull List<ItemStack> items);

    void setItem(int slot, @NotNull ItemStack item);

    boolean addItem(@NotNull ItemStack item);

    void removeItem(@NotNull ItemStack item);

    void clear();
}
