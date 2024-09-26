package me.glicz.airflow.api.item;

import me.glicz.airflow.api.item.component.ItemComponentMap;
import org.jetbrains.annotations.NotNull;

public interface ItemType extends ItemTypeLike {
    @NotNull ItemComponentMap getItemComponentMap();

    @Override
    default @NotNull ItemType asItemType() {
        return this;
    }
}
