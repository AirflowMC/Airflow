package me.glicz.airflow.item.component;

import me.glicz.airflow.api.item.component.ItemComponentType;
import net.kyori.adventure.key.Key;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import org.jetbrains.annotations.NotNull;

public class AirItemComponentType implements ItemComponentType {
    public final DataComponentType<?> handle;

    protected AirItemComponentType(DataComponentType<?> handle) {
        this.handle = handle;
    }

    @Override
    public boolean isPersistent() {
        return !this.handle.isTransient();
    }

    @Override
    public @NotNull Key key() {
        //noinspection PatternValidation
        return Key.key(BuiltInRegistries.DATA_COMPONENT_TYPE.getKey(handle).toString());
    }

    public static class Valued<T> extends AirItemComponentType implements ItemComponentType.Valued<T> {
        public Valued(DataComponentType<?> handle) {
            super(handle);
        }
    }

    public static class NonValued extends AirItemComponentType implements ItemComponentType.NonValued {
        public NonValued(DataComponentType<?> handle) {
            super(handle);
        }
    }
}
