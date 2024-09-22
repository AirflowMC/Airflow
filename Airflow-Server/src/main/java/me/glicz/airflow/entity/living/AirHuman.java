package me.glicz.airflow.entity.living;

import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;

import java.util.EnumSet;
import java.util.List;

public class AirHuman extends AirHumanoid implements me.glicz.airflow.api.entity.living.Human {
    public AirHuman(Human handle) {
        super(handle);
    }

    @Override
    protected ClientboundPlayerInfoUpdatePacket createPlayerInfoUpdatePacket(ClientboundPlayerInfoUpdatePacket.Action... actions) {
        return new ClientboundPlayerInfoUpdatePacket(EnumSet.copyOf(List.of(actions)), getHandle().playerInfoEntry());
    }

    @Override
    public Human getHandle() {
        return (Human) super.getHandle();
    }
}
