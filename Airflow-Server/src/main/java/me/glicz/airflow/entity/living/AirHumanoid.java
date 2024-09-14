package me.glicz.airflow.entity.living;

import me.glicz.airflow.api.entity.living.Humanoid;
import me.glicz.airflow.api.inventory.entity.PlayerInventory;
import me.glicz.airflow.inventory.entity.AirPlayerInventory;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class AirHumanoid extends AirLivingEntity implements Humanoid {
    private final PlayerInventory inventory;

    public AirHumanoid(Player handle) {
        super(handle);
        this.inventory = new AirPlayerInventory(this);
    }

    @Override
    public Player getHandle() {
        return (Player) super.getHandle();
    }

    @Override
    public @NotNull PlayerInventory getInventory() {
        return this.inventory;
    }
}
