package me.glicz.airflow.api.item;

import me.glicz.airflow.api.util.LazyReference;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.ApiStatus;

import static me.glicz.airflow.api.util.ServiceUtils.lazyLoadService;

@ApiStatus.Internal
public abstract class ItemTypeProvider {
    private static final LazyReference<ItemTypeProvider> INSTANCE = lazyLoadService(ItemTypeProvider.class);

    static ItemTypeProvider provider() {
        return INSTANCE.get();
    }

    protected abstract ItemType get(Key key);
}
