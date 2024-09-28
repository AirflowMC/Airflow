package me.glicz.airflow.api.event.inventory;

import me.glicz.airflow.api.entity.living.Humanoid;
import me.glicz.airflow.api.event.Cancellable;
import me.glicz.airflow.api.inventory.Inventory;
import me.glicz.airflow.api.inventory.menu.view.MenuView;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public class InventoryClickEvent extends InventoryEvent implements Cancellable {
    private final int slot;
    private boolean cancelled;

    @ApiStatus.Internal
    public InventoryClickEvent(@NotNull Humanoid viewer, @NotNull MenuView menuView, Inventory inventory, int slot) {
        super(viewer, menuView, inventory);
        this.slot = slot;
    }

    public int getSlot() {
        return slot;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
