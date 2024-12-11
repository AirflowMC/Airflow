package me.glicz.airflow.api.inventory.menu.view;

import me.glicz.airflow.api.entity.living.Humanoid;
import me.glicz.airflow.api.inventory.ComposedInventory;
import me.glicz.airflow.api.inventory.Inventory;
import me.glicz.airflow.api.inventory.entity.PlayerInventory;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public interface MenuView {
    @NotNull Humanoid getViewer();

    @NotNull Component getTitle();

    void setTitle(@NotNull Component title);

    /**
     * Usually primary inventory is the "main" inventory in current view.
     * <p>
     * Depending on the implementation, the primary inventory might be the input slots container etc.
     *
     * @return the base inventory
     */
    @NotNull Inventory getPrimaryInventory();

    @NotNull ComposedInventory getComposedInventory();

    default @NotNull PlayerInventory getViewerInventory() {
        return getViewer().getInventory();
    }

    @NotNull Inventory getInventoryForSlot(int slot);
}
