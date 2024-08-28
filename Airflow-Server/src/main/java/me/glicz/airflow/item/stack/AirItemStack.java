package me.glicz.airflow.item.stack;

import me.glicz.airflow.api.item.ItemType;
import me.glicz.airflow.api.item.stack.ItemStack;

public class AirItemStack implements ItemStack {
    private final net.minecraft.world.item.ItemStack handle;

    public AirItemStack(net.minecraft.world.item.ItemStack handle) {
        this.handle = handle;
    }

    @Override
    public ItemType getType() {
        return handle.getItem().airItemType;
    }
}
