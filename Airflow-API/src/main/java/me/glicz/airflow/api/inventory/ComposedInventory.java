package me.glicz.airflow.api.inventory;

import java.util.Collection;

public interface ComposedInventory extends Inventory {
    Collection<Inventory> getInventories();
}
