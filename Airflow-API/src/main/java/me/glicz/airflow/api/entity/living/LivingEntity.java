package me.glicz.airflow.api.entity.living;

import me.glicz.airflow.api.entity.Entity;

public interface LivingEntity extends Entity {
    float getHealth();

    void setHealth(float health);

    boolean isDead();
}
