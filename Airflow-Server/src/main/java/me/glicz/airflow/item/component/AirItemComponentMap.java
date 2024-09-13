package me.glicz.airflow.item.component;

import me.glicz.airflow.api.item.component.ItemComponentMap;
import me.glicz.airflow.api.item.component.ItemComponentType;
import me.glicz.airflow.item.component.adapter.ItemComponentAdapters;
import net.minecraft.core.component.DataComponentMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
    public String toString() {
        return getHandle().toString();
    }
}
