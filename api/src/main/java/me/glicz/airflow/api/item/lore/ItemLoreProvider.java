package me.glicz.airflow.api.item.lore;

import me.glicz.airflow.api.util.LazyReference;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;

import static me.glicz.airflow.api.util.ServiceUtils.lazyLoadService;

@ApiStatus.Internal
public abstract class ItemLoreProvider {
    private static final LazyReference<ItemLoreProvider> INSTANCE = lazyLoadService(ItemLoreProvider.class);

    static ItemLoreProvider provider() {
        return INSTANCE.get();
    }

    protected abstract ItemLore create(List<Component> lines);
}
