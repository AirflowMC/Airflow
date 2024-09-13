package me.glicz.airflow.api.item.component;

import org.jetbrains.annotations.NotNull;

public interface ItemComponentMap {
    boolean has(@NotNull ItemComponentType type);

    <T> T get(ItemComponentType.@NotNull Valued<T> type);
}
