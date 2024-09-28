package me.glicz.airflow.api.inventory;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface ComposedInventory extends Inventory {
    @NotNull Collection<Inventory> getInventories();

    @NotNull Inventory getInventoryForSlot(int slot);
}
