package me.glicz.airflow.inventory.menu.view;

import me.glicz.airflow.api.inventory.menu.view.AnvilView;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ResultContainer;
import org.jetbrains.annotations.Nullable;

public class AirAnvilView extends AirItemCombinerView implements AnvilView {
    public AirAnvilView(Player player, AnvilMenu containerMenu, Container container, ResultContainer resultContainer) {
        super(player, containerMenu, container, resultContainer);
    }

    @Override
    public AnvilMenu getContainerMenu() {
        return (AnvilMenu) super.getContainerMenu();
    }

    @Override
    public @Nullable String getInputText() {
        return getContainerMenu().getItemName();
    }
}
