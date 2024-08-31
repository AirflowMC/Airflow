package me.glicz.airflow.api.entity.living;

public interface LivingEntity {
    float getHealth();

    void setHealth(float health);

    boolean isDead();
}
