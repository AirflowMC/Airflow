package me.glicz.airflow.api.item.component;

import org.jetbrains.annotations.NotNull;

import java.util.Set;

public interface ItemComponentMap {
    boolean has(@NotNull ItemComponentType type);

    <T> T get(ItemComponentType.@NotNull Valued<T> type);

    @NotNull Set<ItemComponentType> keySet();
}
