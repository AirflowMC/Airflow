package me.glicz.airflow.entity.living;

import me.glicz.airflow.api.entity.living.Humanoid;
import me.glicz.airflow.api.inventory.entity.PlayerInventory;
import me.glicz.airflow.api.inventory.menu.view.MenuView;
import me.glicz.airflow.api.util.LazyReference;
import me.glicz.airflow.inventory.entity.AirPlayerInventory;
import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AirHumanoid extends AirLivingEntity implements Humanoid {
    private final LazyReference<PlayerInventory> inventory = LazyReference.lazy(() -> new AirPlayerInventory(this));
    protected Component playerListName;

    public AirHumanoid(Player handle) {
        super(handle);
    }

    protected abstract ClientboundPlayerInfoUpdatePacket createPlayerInfoUpdatePacket(ClientboundPlayerInfoUpdatePacket.Action... actions);

    @Override
    public Player getHandle() {
        return (Player) super.getHandle();
    }

    @Override
    public @NotNull PlayerInventory getInventory() {
        return this.inventory.get();
    }

    @Override
    public @NotNull MenuView getMenuView() {
        return getHandle().containerMenu.getAirMenuView();
    }

    @Override
    public @Nullable Component getPlayerListName() {
        return this.playerListName;
    }

    @Override
    public void setPlayerListName(@Nullable Component name) {
        this.playerListName = name;

        this.server.minecraftServer.getPlayerList().broadcastAll(createPlayerInfoUpdatePacket(
                ClientboundPlayerInfoUpdatePacket.Action.UPDATE_DISPLAY_NAME
        ));
    }

    @Override
    public boolean isOperator() {
        return server.minecraftServer.getPlayerList().isOp(getHandle().getGameProfile());
    }

    @Override
    public void setOperator(boolean operator) {
        if (operator) {
            server.minecraftServer.getPlayerList().op(getHandle().getGameProfile());
        } else {
            server.minecraftServer.getPlayerList().deop(getHandle().getGameProfile());
        }
    }
}
