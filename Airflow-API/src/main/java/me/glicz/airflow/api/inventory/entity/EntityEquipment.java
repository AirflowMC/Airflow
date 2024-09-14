package me.glicz.airflow.api.inventory.entity;

import me.glicz.airflow.api.item.stack.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface EntityEquipment {
    @NotNull ItemStack getItem(@NotNull EquipmentSlot slot);

    @NotNull Collection<ItemStack> getItems(EquipmentSlotGroup group);

    void setItem(@NotNull EquipmentSlot slot, @NotNull ItemStack itemStack);
}
