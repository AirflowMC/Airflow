package me.glicz.airflow.inventory.menu.view;

import me.glicz.airflow.api.inventory.Inventory;
import me.glicz.airflow.api.inventory.menu.view.LoomView;
import me.glicz.airflow.inventory.AirComposedInventory;
import me.glicz.airflow.inventory.AirSimpleInventory;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.NotNull;

public class AirLoomView extends AirMenuView implements LoomView {
    private final AirSimpleInventory outputContainer;

    public AirLoomView(Player player, AbstractContainerMenu containerMenu, Container container, Container outputContainer) {
        super(player, containerMenu, container);
        this.outputContainer = new AirSimpleInventory(outputContainer);
    }

    @Override
    protected AirComposedInventory createComposedInventory() {
        return new AirComposedInventory(this.primaryInventory, outputContainer);
    }

    @Override
    public @NotNull Inventory getOutputInventory() {
        return outputContainer;
    }
}
