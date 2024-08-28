package me.glicz.airflow.api.generator.entity;

import me.glicz.airflow.api.entity.EntityType;
import me.glicz.airflow.api.entity.EntityTypeProvider;
import me.glicz.airflow.api.generator.Generator;
import net.minecraft.core.registries.BuiltInRegistries;

public class EntityTypesGenerator extends Generator {
    public EntityTypesGenerator() {
        super(BuiltInRegistries.ENTITY_TYPE, "me.glicz.airflow.api.entity", "EntityTypes", EntityType.class, EntityTypeProvider.class);
    }
}
