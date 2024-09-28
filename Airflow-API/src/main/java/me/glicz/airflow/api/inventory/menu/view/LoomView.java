package me.glicz.airflow.api.inventory.menu.view;

import me.glicz.airflow.api.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public interface LoomView extends MenuView {
    @NotNull Inventory getOutputInventory();
}
