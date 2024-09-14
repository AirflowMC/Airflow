package me.glicz.airflow.inventory.entity;

import com.google.common.base.Preconditions;
import me.glicz.airflow.api.inventory.entity.PlayerInventory;
import me.glicz.airflow.api.item.stack.ItemStack;
import me.glicz.airflow.entity.living.AirHumanoid;
import me.glicz.airflow.item.stack.AirItemStack;
import net.minecraft.network.protocol.game.ClientboundSetCarriedItemPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.List;

public class AirPlayerInventory extends AirEntityEquipment implements PlayerInventory {
    private final AirHumanoid player;

    public AirPlayerInventory(AirHumanoid player) {
        super(player);
        this.player = player;
    }

    @Override
    public @NotNull List<ItemStack> getItems() {
        return this.player.getHandle().getInventory().items.stream().<ItemStack>map(itemStack -> itemStack.airItemStack).toList();
    }

    @Override
    public void setItems(@NotNull List<ItemStack> items) {
        Preconditions.checkArgument(items.size() <= this.player.getHandle().getInventory().items.size(), "items size > inventory size");

        this.player.getHandle().getInventory().items.clear();
        this.player.getHandle().getInventory().items.addAll(items.stream().map(itemStack -> ((AirItemStack) itemStack).handle).toList());
    }

    @Override
    public void setItem(int slot, @NotNull ItemStack item) {
        this.player.getHandle().getInventory().setItem(slot, ((AirItemStack) item).handle);
    }

    @Override
    public boolean addItem(@NotNull ItemStack item) {
        return this.player.getHandle().getInventory().add(((AirItemStack) item).handle);
    }

    @Override
    public void removeItem(@NotNull ItemStack item) {
        this.player.getHandle().getInventory().removeItem(((AirItemStack) item).handle);
    }

    @Override
    public void clear() {
        this.player.getHandle().getInventory().clearContent();
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
}
