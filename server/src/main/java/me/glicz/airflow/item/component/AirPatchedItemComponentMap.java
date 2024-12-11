package me.glicz.airflow.item.component;

import me.glicz.airflow.api.item.component.ItemComponentType;
import me.glicz.airflow.api.item.component.PatchedItemComponentMap;
import me.glicz.airflow.item.component.adapter.ItemComponentAdapters;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.util.Unit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AirPatchedItemComponentMap extends AirItemComponentMap implements PatchedItemComponentMap {
    public AirPatchedItemComponentMap(PatchedDataComponentMap handle) {
        super(handle);
    }

    @Override
    public PatchedDataComponentMap getHandle() {
        return (PatchedDataComponentMap) super.getHandle();
    }

    @Override
    public void set(ItemComponentType.@NotNull NonValued type) {
        set0(type, Unit.INSTANCE);
    }

    @Override
    public <T> void set(ItemComponentType.@NotNull Valued<T> type, @Nullable T value) {
        if (value == null) {
            remove(type);
            return;
        }

        set0(type, ItemComponentAdapters.get(type).asMinecraft(value));
    }

    private void set0(ItemComponentType type, Object value) {
        //noinspection unchecked
        getHandle().set((DataComponentType<Object>) ((AirItemComponentType) type).handle, value);
    }

    @Override
    public void remove(@NotNull ItemComponentType type) {
        getHandle().remove(((AirItemComponentType.Valued<?>) type).handle);
    }
}
