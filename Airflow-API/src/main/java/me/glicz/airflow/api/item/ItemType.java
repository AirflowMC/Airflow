package me.glicz.airflow.api.item;

import net.kyori.adventure.key.Keyed;
import net.kyori.adventure.translation.Translatable;

public interface ItemType extends ItemTypeLike, Keyed, Translatable {
    @Override
    default ItemType getItemType() {
        return this;
    }
}
