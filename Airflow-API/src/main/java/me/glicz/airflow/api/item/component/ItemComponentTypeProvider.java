package me.glicz.airflow.api.item.component;

import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.ApiStatus;

import java.util.ServiceLoader;

@ApiStatus.Internal
public abstract class ItemComponentTypeProvider {
    private static ItemComponentTypeProvider instance = null;

    static ItemComponentTypeProvider provider() {
        if (instance == null) {
            instance = ServiceLoader.load(ItemComponentTypeProvider.class).findFirst().orElseThrow();
        }

        return instance;
    }

    protected abstract <T> ItemComponentType.Valued<T> getValued(Key key);

    protected abstract ItemComponentType.NonValued getNonValued(Key key);
}
