package me.glicz.airflow.entity.living;

import me.glicz.airflow.api.entity.living.Player;
import net.minecraft.server.level.ServerPlayer;

public class AirPlayer extends AirHumanoid implements Player {
    public AirPlayer(ServerPlayer handle) {
        super(handle);
    }

    @Override
    public ServerPlayer getHandle() {
        return (ServerPlayer) super.getHandle();
    }

    @Override
    public boolean isOperator() {
        return getHandle().getServer().getPlayerList().isOp(getHandle().getGameProfile());
    }
}
