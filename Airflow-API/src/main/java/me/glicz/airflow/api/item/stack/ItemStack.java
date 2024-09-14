package me.glicz.airflow.api.item.stack;

import me.glicz.airflow.api.item.ItemType;
import me.glicz.airflow.api.item.component.PatchedItemComponentMap;
import me.glicz.airflow.api.util.Typed;
import org.jetbrains.annotations.NotNull;

public interface ItemStack extends Typed<ItemType> {
    @NotNull PatchedItemComponentMap getItemComponentMap();

    boolean isEmpty();
}
