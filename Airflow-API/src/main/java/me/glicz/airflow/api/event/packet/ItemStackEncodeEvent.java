package me.glicz.airflow.api.event.packet;

import me.glicz.airflow.api.event.Event;
import me.glicz.airflow.api.item.stack.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public class ItemStackEncodeEvent extends Event {
    private ItemStack itemStack;

    @ApiStatus.Internal
    public ItemStackEncodeEvent(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public @NotNull ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(@NotNull ItemStack itemStack) {
        this.itemStack = itemStack;
    }
}
