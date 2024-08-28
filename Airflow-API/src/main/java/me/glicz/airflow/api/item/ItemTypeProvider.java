package me.glicz.airflow.api.item;

import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.ApiStatus;

import java.util.ServiceLoader;

@ApiStatus.Internal
public abstract class ItemTypeProvider {
    private static ItemTypeProvider instance = null;

    static ItemTypeProvider provider() {
        if (instance == null) {
            instance = ServiceLoader.load(ItemTypeProvider.class).findFirst().orElseThrow();
        }

        return instance;
    }

    protected abstract ItemType get(Key key);
}
