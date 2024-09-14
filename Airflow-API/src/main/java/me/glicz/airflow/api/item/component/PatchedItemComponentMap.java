package me.glicz.airflow.api.item.component;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public interface PatchedItemComponentMap extends ItemComponentMap {
    PatchedItemComponentMap EMPTY = new PatchedItemComponentMap() {
        @Override
        public boolean has(@NotNull ItemComponentType type) {
            return false;
        }

        @Override
        public <T> T get(@NotNull ItemComponentType.Valued<T> type) {
            return null;
        }

        @Override
        public @NotNull Set<ItemComponentType> keySet() {
            return Set.of();
        }

        @Override
        public void set(ItemComponentType.@NotNull NonValued type) {
        }

        @Override
        public <T> void set(@NotNull ItemComponentType.Valued<T> type, @Nullable T value) {
        }

        @Override
        public void remove(@NotNull ItemComponentType type) {
        }
    };

    void set(ItemComponentType.@NotNull NonValued type);

    <T> void set(ItemComponentType.@NotNull Valued<T> type, @Nullable T value);

    void remove(@NotNull ItemComponentType type);
}
