package me.glicz.airflow.api.item;

import me.glicz.airflow.api.item.stack.ItemStack;
import net.kyori.adventure.translation.Translatable;

public interface ItemType extends Translatable {
    default ItemStack asItemStack() {
        return asItemStack(1);
    }

    ItemStack asItemStack(int amount);
}
