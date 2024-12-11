package me.glicz.airflow.api.block.state;

import me.glicz.airflow.api.util.LazyReference;
import org.jetbrains.annotations.ApiStatus;

import static me.glicz.airflow.api.util.ServiceUtils.lazyLoadService;

@ApiStatus.Internal
public abstract class BlockStatePropertyProvider {
    private static final LazyReference<BlockStatePropertyProvider> INSTANCE = lazyLoadService(BlockStatePropertyProvider.class);

    static BlockStatePropertyProvider provider() {
        return INSTANCE.get();
    }

    protected abstract BlockStateProperty.Boolean getBoolean(String name);

    protected abstract BlockStateProperty.Integer getInteger(String name);
}
