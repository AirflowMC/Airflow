package me.glicz.airflow.api.item;

import org.jetbrains.annotations.NotNull;

public interface ItemType extends ItemTypeLike {
    @Override
    default @NotNull ItemType getItemType() {
        return this;
    }
}
