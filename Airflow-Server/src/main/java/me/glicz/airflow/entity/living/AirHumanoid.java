package me.glicz.airflow.entity.living;

import me.glicz.airflow.api.entity.living.Humanoid;
import net.minecraft.world.entity.player.Player;

public class AirHumanoid extends AirLivingEntity implements Humanoid {
    public AirHumanoid(Player handle) {
        super(handle);
    }

    @Override
    public Player getHandle() {
        return (Player) super.getHandle();
    }
}
