package me.glicz.airflow.api.inventory.entity;

import me.glicz.airflow.api.inventory.Inventory;
import me.glicz.airflow.api.item.stack.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public interface PlayerInventory extends EntityEquipment, Inventory {
    int getSelectedSlot();

    void setSelectedSlot(@Range(from = 0, to = 8) int slot);

    @NotNull ItemStack getSelectedItem();
}
