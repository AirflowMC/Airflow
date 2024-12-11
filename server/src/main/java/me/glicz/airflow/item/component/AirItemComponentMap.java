package me.glicz.airflow.item.component;

import me.glicz.airflow.api.item.component.ItemComponentMap;
import me.glicz.airflow.api.item.component.ItemComponentType;
import me.glicz.airflow.item.component.adapter.ItemComponentAdapters;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class AirItemComponentMap implements ItemComponentMap {
    private final DataComponentMap handle;

    public AirItemComponentMap(DataComponentMap handle) {
        this.handle = handle;
    }

    public DataComponentMap getHandle() {
        return handle;
    }

    @Override
    public boolean has(@NotNull ItemComponentType type) {
        return getHandle().has(((AirItemComponentType.Valued<?>) type).handle);
    }

    @Override
    public <T> @Nullable T get(ItemComponentType.@NotNull Valued<T> type) {
        AirItemComponentType.Valued<T> airType = (AirItemComponentType.Valued<T>) type;

        return ItemComponentAdapters.get(airType).asAir(getHandle().get(airType.handle));
    }

    @Override
    public @NotNull Set<ItemComponentType> keySet() {
        return getHandle().keySet().stream().map(DataComponentType::airItemComponentType).collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public String toString() {
        return getHandle().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AirItemComponentMap that)) return false;
        return Objects.equals(this.handle, that.handle);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(handle);
    }
}
