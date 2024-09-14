package me.glicz.airflow.api.item.lore;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;
import java.util.ServiceLoader;

@ApiStatus.Internal
public abstract class ItemLoreProvider {
    private static ItemLoreProvider instance = null;

    static ItemLoreProvider provider() {
        if (instance == null) {
            instance = ServiceLoader.load(ItemLoreProvider.class).findFirst().orElseThrow();
        }

        return instance;
    }

    protected abstract ItemLore create(List<Component> lines);
}
