package me.glicz.airflow.inventory.menu.view;

import me.glicz.airflow.api.inventory.Inventory;
import me.glicz.airflow.api.inventory.menu.view.ItemCombinerView;
import me.glicz.airflow.inventory.AirComposedInventory;
import me.glicz.airflow.inventory.AirSimpleInventory;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ResultContainer;
import org.jetbrains.annotations.NotNull;

public class AirItemCombinerView extends AirMenuView implements ItemCombinerView {
    private final AirSimpleInventory resultInventory;

    public AirItemCombinerView(Player player, AbstractContainerMenu containerMenu, Container container, ResultContainer resultContainer) {
        super(player, containerMenu, container);
        this.resultInventory = new AirSimpleInventory(resultContainer);
    }

    @Override
    protected AirComposedInventory createComposedInventory() {
        return new AirComposedInventory(this.primaryInventory, this.resultInventory);
    }

    @Override
    public @NotNull Inventory getResultInventory() {
        return this.resultInventory;
    }
}
