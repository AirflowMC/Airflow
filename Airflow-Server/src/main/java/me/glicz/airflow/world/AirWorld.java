package me.glicz.airflow.world;

import me.glicz.airflow.api.Server;
import me.glicz.airflow.api.block.Block;
import me.glicz.airflow.api.util.math.Vector3i;
import me.glicz.airflow.api.world.World;
import me.glicz.airflow.block.AirBlock;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.ServerLevelData;
import org.jetbrains.annotations.NotNull;

public class AirWorld implements World {
    public final ServerLevel handle;

    public AirWorld(ServerLevel handle) {
        this.handle = handle;
    }

    @Override
    public @NotNull String getName() {
        return ((ServerLevelData) this.handle.getLevelData()).getLevelName();
    }

    @Override
    public @NotNull Block getBlockAt(int x, int y, int z) {
        return new AirBlock(this.handle, new Vector3i(x, y, z));
    }

    @Override
    public Server getServer() {
        return this.handle.getServer().getDedicatedServer().airflow.getServer();
    }
}
