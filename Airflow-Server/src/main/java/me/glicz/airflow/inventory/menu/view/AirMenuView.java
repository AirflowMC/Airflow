package me.glicz.airflow.inventory.menu.view;

import me.glicz.airflow.api.entity.living.Humanoid;
import me.glicz.airflow.api.inventory.ComposedInventory;
import me.glicz.airflow.api.inventory.Inventory;
import me.glicz.airflow.api.inventory.menu.view.MenuView;
import me.glicz.airflow.entity.living.AirHumanoid;
import me.glicz.airflow.entity.living.AirPlayer;
import me.glicz.airflow.inventory.AirComposedInventory;
import me.glicz.airflow.inventory.AirSimpleInventory;
import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.NotNull;

public class AirMenuView implements MenuView {
    protected final AirSimpleInventory primaryInventory;
    private final AirHumanoid viewer;
    private final AbstractContainerMenu containerMenu;
    private final AirComposedInventory composedInventory;
    public boolean pluginOpened = false;
    private Component title;

    public AirMenuView(Player player, AbstractContainerMenu containerMenu, Container container) {
        this.viewer = player.getAirEntity();
        this.containerMenu = containerMenu;
        this.primaryInventory = new AirSimpleInventory(container);
        this.composedInventory = createComposedInventory();
    }

    public AbstractContainerMenu getContainerMenu() {
        return containerMenu;
    }

    protected AirComposedInventory createComposedInventory() {
        return new AirComposedInventory(this.primaryInventory);
    }

    @Override
    public @NotNull Humanoid getViewer() {
        return this.viewer;
    }

    @Override
    public @NotNull Component getTitle() {
        return this.title;
    }

    @Override
    public void setTitle(@NotNull Component title) {
        this.title = title;
        sendTitle(this.viewer.componentSerializer().serialize(title));
    }

    public void setTitle(net.minecraft.network.chat.Component title) {
        this.title = this.viewer.componentSerializer().deserialize(title);
        sendTitle(title);
    }

    private void sendTitle(net.minecraft.network.chat.Component title) {
        if (this.viewer instanceof AirPlayer player) {
            player.getHandle().connection.send(new ClientboundOpenScreenPacket(
                    this.containerMenu.containerId,
                    this.containerMenu.getType(),
                    title
            ));
        }
    }

    @Override
    public @NotNull Inventory getPrimaryInventory() {
        return this.primaryInventory;
    }

    @Override
    public @NotNull ComposedInventory getComposedInventory() {
        return this.composedInventory;
    }
}
