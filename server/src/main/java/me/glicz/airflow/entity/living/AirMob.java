package me.glicz.airflow.entity.living;

import net.minecraft.world.entity.Mob;

public class AirMob extends AirLivingEntity implements me.glicz.airflow.api.entity.living.Mob {
    public AirMob(Mob handle) {
        super(handle);
    }

    @Override
    public Mob getHandle() {
        return (Mob) super.getHandle();
    }
}
