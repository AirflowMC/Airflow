package me.glicz.airflow.entity;

import me.glicz.airflow.api.entity.EntityType;
import me.glicz.airflow.api.entity.EntityTypeProvider;
import net.kyori.adventure.key.Key;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class AirEntityTypeProvider extends EntityTypeProvider {
    @Override
    protected EntityType get(Key key) {
        return BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.parse(key.asString())).airEntityType;
    }
}
