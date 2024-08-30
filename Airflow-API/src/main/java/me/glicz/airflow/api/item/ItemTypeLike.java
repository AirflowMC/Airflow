package me.glicz.airflow.api.item;

import me.glicz.airflow.api.item.stack.ItemStack;

public interface ItemTypeLike {
    ItemType getItemType();

    default ItemStack asItemStack() {
        return asItemStack(1);
    }

    ItemStack asItemStack(int amount);
}
