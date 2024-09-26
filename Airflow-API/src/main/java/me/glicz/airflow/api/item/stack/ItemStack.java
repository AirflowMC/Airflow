package me.glicz.airflow.api.item.stack;

import me.glicz.airflow.api.item.ItemType;
import me.glicz.airflow.api.item.component.PatchedItemComponentMap;
import me.glicz.airflow.api.util.Typed;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface ItemStack extends Typed<ItemType> {
    int getAmount();

    void setAmount(int amount);

    @Contract(value = "_ -> new", pure = true)
    @NotNull ItemStack withAmount(int amount);

    boolean isEmpty();

    @NotNull PatchedItemComponentMap getItemComponentMap();
}
