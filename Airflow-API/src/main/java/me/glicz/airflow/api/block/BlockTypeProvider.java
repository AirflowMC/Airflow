package me.glicz.airflow.api.block;

import me.glicz.airflow.api.util.LazyReference;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.ApiStatus;

import static me.glicz.airflow.api.util.ServiceUtils.lazyLoadService;

@ApiStatus.Internal
public abstract class BlockTypeProvider {
    private static final LazyReference<BlockTypeProvider> INSTANCE = lazyLoadService(BlockTypeProvider.class);

    static BlockTypeProvider provider() {
        return INSTANCE.get();
    }

    protected abstract BlockType get(Key key);
}
