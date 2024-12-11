package me.glicz.airflow.api.world;

import me.glicz.airflow.api.ServerAware;
import me.glicz.airflow.api.block.Block;
import me.glicz.airflow.api.entity.Entity;
import me.glicz.airflow.api.entity.living.Player;
import me.glicz.airflow.api.util.math.Vector3i;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.UUID;

public interface World extends ServerAware {
    @NotNull String getName();

    default @NotNull Block getBlockAt(@NotNull Vector3i position) {
        return getBlockAt(position.x(), position.y(), position.z());
    }

    @NotNull Block getBlockAt(int x, int y, int z);

    @Nullable Entity getEntity(@NotNull UUID uniqueId);

    @NotNull Collection<Entity> getEntities();

    @Nullable Player getPlayer(@NotNull UUID uniqueId);

    @NotNull Collection<Player> getPlayers();
}
