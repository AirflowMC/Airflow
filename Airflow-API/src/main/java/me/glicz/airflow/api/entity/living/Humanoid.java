package me.glicz.airflow.api.entity.living;

import me.glicz.airflow.api.inventory.entity.PlayerInventory;
import org.jetbrains.annotations.NotNull;

public interface Humanoid extends LivingEntity {
    @NotNull PlayerInventory getInventory();
}
