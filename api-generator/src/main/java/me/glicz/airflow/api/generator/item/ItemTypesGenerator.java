package me.glicz.airflow.api.generator.item;

import me.glicz.airflow.api.generator.RegistryBasedGenerator;
import me.glicz.airflow.api.item.ItemType;
import me.glicz.airflow.api.item.ItemTypeProvider;
import net.minecraft.core.registries.BuiltInRegistries;

public class ItemTypesGenerator extends RegistryBasedGenerator {
    public ItemTypesGenerator() {
        super(BuiltInRegistries.ITEM, "me.glicz.airflow.api.item", "ItemTypes", ItemType.class, ItemTypeProvider.class);
    }
}
