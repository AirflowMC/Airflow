package me.glicz.airflow.entity;

import me.glicz.airflow.api.entity.Entity;
import me.glicz.airflow.api.entity.EntityType;
import me.glicz.airflow.api.entity.EntityTypeProvider;
import net.kyori.adventure.key.Key;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class AirEntityTypeProvider extends EntityTypeProvider {
    @Override
    protected <T extends Entity> EntityType<T> get(Key key) {
        //noinspection unchecked
        return (EntityType<T>) BuiltInRegistries.ENTITY_TYPE.getValue(ResourceLocation.parse(key.asString())).airEntityType;
    }
}
