package me.glicz.airflow.api.block;

import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.ApiStatus;

import java.util.ServiceLoader;

@ApiStatus.Internal
public abstract class BlockTypeProvider {
    private static BlockTypeProvider instance = null;

    static BlockTypeProvider provider() {
        if (instance == null) {
            instance = ServiceLoader.load(BlockTypeProvider.class).findFirst().orElseThrow();
        }

        return instance;
    }

    protected abstract BlockType get(Key key);
}
