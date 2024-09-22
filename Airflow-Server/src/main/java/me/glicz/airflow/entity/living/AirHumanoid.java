package me.glicz.airflow.entity.living;

import me.glicz.airflow.api.entity.living.Humanoid;
import me.glicz.airflow.api.inventory.entity.PlayerInventory;
import me.glicz.airflow.inventory.entity.AirPlayerInventory;
import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AirHumanoid extends AirLivingEntity implements Humanoid {
    private final PlayerInventory inventory;
    protected Component playerListName;

    public AirHumanoid(Player handle) {
        super(handle);
        this.inventory = new AirPlayerInventory(this);
    }

    protected abstract ClientboundPlayerInfoUpdatePacket createPlayerInfoUpdatePacket(ClientboundPlayerInfoUpdatePacket.Action... actions);

    @Override
    public Player getHandle() {
        return (Player) super.getHandle();
    }

    @Override
    public @NotNull PlayerInventory getInventory() {
        return this.inventory;
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
}
