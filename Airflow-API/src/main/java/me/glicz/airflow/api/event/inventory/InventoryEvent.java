package me.glicz.airflow.api.event.inventory;

import me.glicz.airflow.api.entity.living.Humanoid;
import me.glicz.airflow.api.event.inventory.menu.MenuEvent;
import me.glicz.airflow.api.inventory.Inventory;
import me.glicz.airflow.api.inventory.menu.view.MenuView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

public abstract class InventoryEvent extends MenuEvent {
    private final Inventory inventory;

    public InventoryEvent(@NotNull Humanoid viewer, @NotNull MenuView menuView, Inventory inventory) {
        super(viewer, menuView);
        this.inventory = inventory;
    }

    public @UnknownNullability Inventory getInventory() {
        return inventory;
    }
}
