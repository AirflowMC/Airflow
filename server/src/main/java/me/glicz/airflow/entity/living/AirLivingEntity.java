package me.glicz.airflow.entity.living;

import me.glicz.airflow.api.inventory.entity.EntityEquipment;
import me.glicz.airflow.entity.AirEntity;
import me.glicz.airflow.inventory.entity.AirEntityEquipment;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class AirLivingEntity extends AirEntity implements me.glicz.airflow.api.entity.living.LivingEntity {
    private final EntityEquipment equipment;

    public AirLivingEntity(LivingEntity handle) {
        super(handle);
        this.equipment = new AirEntityEquipment(this);
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

    @Override
    public @NotNull EntityEquipment getEquipment() {
        return this.equipment;
    }
}
