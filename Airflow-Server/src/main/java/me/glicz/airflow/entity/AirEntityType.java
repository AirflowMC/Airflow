package me.glicz.airflow.entity;

import me.glicz.airflow.api.entity.Entity;
import me.glicz.airflow.api.entity.EntityType;
import net.kyori.adventure.key.Key;
import net.minecraft.core.registries.BuiltInRegistries;
import org.jetbrains.annotations.NotNull;

public class AirEntityType<T extends Entity> implements EntityType<T> {
    private final net.minecraft.world.entity.EntityType<?> handle;

    public AirEntityType(net.minecraft.world.entity.EntityType<?> handle) {
        this.handle = handle;
    }

    @Override
    public @NotNull Key key() {
        //noinspection PatternValidation
        return Key.key(BuiltInRegistries.ENTITY_TYPE.getKey(this.handle).toString());
    }

    @Override
    public @NotNull String translationKey() {
        return this.handle.getDescriptionId();
    }
}
