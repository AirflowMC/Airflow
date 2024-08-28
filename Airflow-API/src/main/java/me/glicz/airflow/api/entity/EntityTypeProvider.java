package me.glicz.airflow.api.entity;

import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.ApiStatus;

import java.util.ServiceLoader;

@ApiStatus.Internal
public abstract class EntityTypeProvider {
    private static EntityTypeProvider instance = null;

    static EntityTypeProvider provider() {
        if (instance == null) {
            instance = ServiceLoader.load(EntityTypeProvider.class).findFirst().orElseThrow();
        }

        return instance;
    }

    protected abstract EntityType get(Key key);
}
