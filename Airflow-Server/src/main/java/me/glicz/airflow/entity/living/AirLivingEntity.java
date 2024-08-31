package me.glicz.airflow.entity.living;

import me.glicz.airflow.entity.AirEntity;
import net.minecraft.world.entity.LivingEntity;

public class AirLivingEntity extends AirEntity implements me.glicz.airflow.api.entity.living.LivingEntity {
    public AirLivingEntity(LivingEntity handle) {
        super(handle);
    }

    @Override
    public LivingEntity getHandle() {
        return (LivingEntity) super.getHandle();
    }

    @Override
    public float getHealth() {
        return getHandle().getHealth();
    }

    @Override
    public void setHealth(float health) {
        getHandle().setHealth(health);
    }

    @Override
    public boolean isDead() {
        return getHandle().isDeadOrDying();
    }
}
