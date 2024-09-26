package me.glicz.airflow.api.inventory.menu.view;

import me.glicz.airflow.api.inventory.Inventory;

public interface ItemCombinerView extends MenuView {
    Inventory getResultInventory();
}
