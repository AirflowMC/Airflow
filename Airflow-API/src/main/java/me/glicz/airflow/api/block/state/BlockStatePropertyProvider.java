package me.glicz.airflow.api.block.state;

import org.jetbrains.annotations.ApiStatus;

import java.util.ServiceLoader;

@ApiStatus.Internal
public abstract class BlockStatePropertyProvider {
    private static BlockStatePropertyProvider instance = null;

    static BlockStatePropertyProvider provider() {
        if (instance == null) {
            instance = ServiceLoader.load(BlockStatePropertyProvider.class).findFirst().orElseThrow();
        }

        return instance;
    }

    protected abstract BlockStateProperty.Boolean getBoolean(String name);

    protected abstract BlockStateProperty.Integer getInteger(String name);
}
