package me.glicz.airflow.api.item;

import net.kyori.adventure.key.Keyed;
import net.kyori.adventure.translation.Translatable;
import org.jetbrains.annotations.NotNull;

public interface ItemType extends ItemTypeLike, Keyed, Translatable {
    @Override
    default @NotNull ItemType getItemType() {
        return this;
    }
}
