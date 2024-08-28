package me.glicz.airflow.entity;

import me.glicz.airflow.api.entity.EntityType;
import org.jetbrains.annotations.NotNull;

public class AirEntityType implements EntityType {
    private final net.minecraft.world.entity.EntityType<?> handle;

    public AirEntityType(net.minecraft.world.entity.EntityType<?> handle) {
        this.handle = handle;
    }

    @Override
    public @NotNull String translationKey() {
        return this.handle.getDescriptionId();
    }
}
