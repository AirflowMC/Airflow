package me.glicz.airflow.item.component;

import me.glicz.airflow.api.item.component.ItemComponentType;
import me.glicz.airflow.api.item.component.ItemComponentTypeProvider;
import net.kyori.adventure.key.Key;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class AirItemComponentTypeProvider extends ItemComponentTypeProvider {
    @Override
    protected <T> ItemComponentType.Valued<T> getValued(Key key) {
        //noinspection unchecked
        return (ItemComponentType.Valued<T>) get(key);
    }

    @Override
    protected ItemComponentType.NonValued getNonValued(Key key) {
        return (ItemComponentType.NonValued) get(key);
    }

    private ItemComponentType get(Key key) {
        return BuiltInRegistries.DATA_COMPONENT_TYPE
                .getOptional(ResourceLocation.parse(key.asString()))
                .map(DataComponentType::airItemComponentType)
                .orElse(null);
    }
}
