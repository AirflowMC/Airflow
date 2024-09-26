package me.glicz.airflow.inventory.entity;

import com.google.common.base.Preconditions;
import me.glicz.airflow.api.inventory.entity.EntityEquipment;
import me.glicz.airflow.api.inventory.entity.EquipmentSlot;
import me.glicz.airflow.api.inventory.entity.EquipmentSlotGroup;
import me.glicz.airflow.api.inventory.entity.PlayerInventory;
import me.glicz.airflow.api.item.stack.ItemStack;
import me.glicz.airflow.entity.living.AirHumanoid;
import me.glicz.airflow.inventory.AirSimpleInventory;
import net.minecraft.network.protocol.game.ClientboundSetCarriedItemPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.Collection;

public class AirPlayerInventory extends AirSimpleInventory implements PlayerInventory, EntityEquipment {
    private final AirHumanoid player;

    public AirPlayerInventory(AirHumanoid player) {
        super(player.getHandle().getInventory());
        this.player = player;
    }

    @Override
    public int getSelectedSlot() {
        return this.player.getHandle().getInventory().selected;
    }

    @Override
    public void setSelectedSlot(@Range(from = 0, to = 8) int slot) {
        Preconditions.checkArgument(Inventory.isHotbarSlot(slot), "slot < 0 || slot > 8");

        this.player.getHandle().getInventory().selected = slot;

        if (this.player.getHandle() instanceof ServerPlayer serverPlayer) {
            serverPlayer.connection.send(new ClientboundSetCarriedItemPacket(slot));
        }
    }

    @Override
    public @NotNull ItemStack getSelectedItem() {
        return this.player.getHandle().getInventory().getSelected().airItemStack;
    }

    @Override
    public @NotNull ItemStack getItem(@NotNull EquipmentSlot slot) {
        return this.player.getEquipment().getItem(slot);
    }

    @Override
    public @NotNull Collection<ItemStack> getItems(EquipmentSlotGroup group) {
        return this.player.getEquipment().getItems(group);
    }

    @Override
    public void setItem(@NotNull EquipmentSlot slot, @NotNull ItemStack itemStack) {
        this.player.getEquipment().setItem(slot, itemStack);
    }
}
