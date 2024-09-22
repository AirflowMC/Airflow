package me.glicz.airflow.api.entity.living;

import me.glicz.airflow.api.inventory.entity.PlayerInventory;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Humanoid extends LivingEntity {
    @NotNull PlayerInventory getInventory();

    @Nullable Component getPlayerListName();

    void setPlayerListName(@Nullable Component name);
}
