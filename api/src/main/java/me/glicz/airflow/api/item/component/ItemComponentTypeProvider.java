package me.glicz.airflow.api.item.component;

import me.glicz.airflow.api.util.LazyReference;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.ApiStatus;

import static me.glicz.airflow.api.util.ServiceUtils.lazyLoadService;

@ApiStatus.Internal
public abstract class ItemComponentTypeProvider {
    private static final LazyReference<ItemComponentTypeProvider> INSTANCE = lazyLoadService(ItemComponentTypeProvider.class);

    static ItemComponentTypeProvider provider() {
        return INSTANCE.get();
    }

    protected abstract <T> ItemComponentType.Valued<T> getValued(Key key);

    protected abstract ItemComponentType.NonValued getNonValued(Key key);
}
