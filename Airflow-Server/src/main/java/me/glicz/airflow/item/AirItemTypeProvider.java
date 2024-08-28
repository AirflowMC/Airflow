package me.glicz.airflow.item;

import me.glicz.airflow.api.item.ItemType;
import me.glicz.airflow.api.item.ItemTypeProvider;
import net.kyori.adventure.key.Key;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class AirItemTypeProvider extends ItemTypeProvider {
    @Override
    protected ItemType get(Key key) {
        return BuiltInRegistries.ITEM.get(ResourceLocation.parse(key.asString())).airItemType;
    }
}
