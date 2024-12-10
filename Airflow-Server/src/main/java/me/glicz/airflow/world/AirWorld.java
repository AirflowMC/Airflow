package me.glicz.airflow.world;

import me.glicz.airflow.api.Server;
import me.glicz.airflow.api.block.Block;
import me.glicz.airflow.api.entity.Entity;
import me.glicz.airflow.api.entity.living.Player;
import me.glicz.airflow.api.util.math.Vector3i;
import me.glicz.airflow.api.world.World;
import me.glicz.airflow.block.AirBlock;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.storage.ServerLevelData;
import org.apache.commons.lang3.stream.Streams;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.UUID;

public class AirWorld implements World {
    public final ServerLevel handle;

    public AirWorld(ServerLevel handle) {
        this.handle = handle;
    }

    @Override
    public @NotNull String getName() {
        return ((ServerLevelData) handle.getLevelData()).getLevelName();
    }

    @Override
    public @NotNull Block getBlockAt(int x, int y, int z) {
        return new AirBlock(handle, new Vector3i(x, y, z));
    }

    @Override
    public @Nullable Entity getEntity(@NotNull UUID uniqueId) {
        net.minecraft.world.entity.Entity entity = handle.getEntity(uniqueId);
        if (entity != null) {
            return entity.getAirEntity();
        }

        return null;
    }

    @Override
    public @NotNull Collection<Entity> getEntities() {
        return Streams.of(handle.getAllEntities())
                .<Entity>map(net.minecraft.world.entity.Entity::getAirEntity)
                .toList();
    }

    @Override
    public @Nullable Player getPlayer(@NotNull UUID uniqueId) {
        ServerPlayer player = (ServerPlayer) handle.getPlayerByUUID(uniqueId);
        if (player != null) {
            return player.getAirEntity();
        }

        return null;
    }

    @Override
    public @NotNull Collection<Player> getPlayers() {
        return handle.players().stream()
                .<Player>map(ServerPlayer::getAirEntity)
                .toList();
    }

    @Override
    public Server getServer() {
        return handle.getServer().getDedicatedServer().airflow.getServer();
    }
}
