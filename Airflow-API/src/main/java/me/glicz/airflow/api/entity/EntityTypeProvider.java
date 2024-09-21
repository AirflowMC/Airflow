package me.glicz.airflow.api.entity;

import me.glicz.airflow.api.util.LazyReference;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.ApiStatus;

import static me.glicz.airflow.api.util.ServiceUtils.lazyLoadService;

@ApiStatus.Internal
public abstract class EntityTypeProvider {
    private static final LazyReference<EntityTypeProvider> INSTANCE = lazyLoadService(EntityTypeProvider.class);

    static EntityTypeProvider provider() {
        return INSTANCE.get();
    }

    protected abstract <T extends Entity> EntityType<T> get(Key key);
}
