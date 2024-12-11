package me.glicz.airflow.api.entity.living;

import me.glicz.airflow.api.entity.Entity;
import me.glicz.airflow.api.inventory.entity.EntityEquipment;
import org.jetbrains.annotations.NotNull;

public interface LivingEntity extends Entity {
    float getHealth();

    void setHealth(float health);

    boolean isDead();

    @NotNull EntityEquipment getEquipment();
}
