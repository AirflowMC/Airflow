package me.glicz.airflow.entity.living;

import com.mojang.authlib.GameProfile;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoRemovePacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.level.GameType;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class Human extends Player {
    private GameType gameType = GameType.DEFAULT_MODE;
    private int latency;

    public Human(ServerLevel level, GameProfile gameProfile) {
        super(EntityType.HUMAN, level, level.getSharedSpawnPos(), level.getSharedSpawnAngle(), gameProfile);

        getEntityData().set(Player.DATA_PLAYER_MODE_CUSTOMISATION, (byte) Arrays.stream(PlayerModelPart.values()).mapToInt(PlayerModelPart::getMask).sum());
    }

    @Override
    protected @NotNull AirHuman createAirEntity() {
        return new AirHuman(this);
    }

    @Override
    public @NotNull AirHuman getAirEntity() {
        return (AirHuman) super.getAirEntity();
    }

    @Override
    public void onUpdateAbilities() {
        updateInvisibilityStatus();
    }

    @Override
    public boolean isSpectator() {
        return gameType == GameType.SPECTATOR;
    }

    @Override
    public boolean isCreative() {
        return gameType == GameType.CREATIVE;
    }

    @Override
    protected void updateInvisibilityStatus() {
        if (this.isSpectator()) {
            this.removeEffectParticles();
            this.setInvisible(true);
        } else {
            super.updateInvisibilityStatus();
        }
    }

    @Override
    public @NotNull ClientboundAddEntityPacket getAddEntityPacket(@NotNull ServerEntity entity) {
        return new ClientboundAddEntityPacket(
                getId(),
                getUUID(),
                entity.getPositionBase().x(),
                entity.getPositionBase().y(),
                entity.getPositionBase().z(),
                entity.getLastSentXRot(),
                entity.getLastSentYRot(),
                EntityType.PLAYER,
                0,
                entity.getLastSentMovement(),
                entity.getLastSentYHeadRot()
        );
    }

    protected ClientboundPlayerInfoUpdatePacket.Entry playerInfoEntry() {
        return new ClientboundPlayerInfoUpdatePacket.Entry(
                getUUID(), getGameProfile(), false, latency, gameType, getTabListDisplayName(), isModelPartShown(PlayerModelPart.HAT), 0, null
        );
    }

    public ClientboundPlayerInfoUpdatePacket createPlayerInfoUpdatePacket() {
        return getAirEntity().createPlayerInfoUpdatePacket(
                ClientboundPlayerInfoUpdatePacket.Action.values()
        );
    }

    public ClientboundPlayerInfoRemovePacket createPlayerInfoRemovePacket() {
        return new ClientboundPlayerInfoRemovePacket(List.of(getUUID()));
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
        onUpdateAbilities();

        getServer().getPlayerList().broadcastAll(getAirEntity().createPlayerInfoUpdatePacket(
                ClientboundPlayerInfoUpdatePacket.Action.UPDATE_GAME_MODE
        ));

        if (isCreative()) {
            resetCurrentImpulseContext();
        }
    }

    public int getLatency() {
        return latency;
    }

    public void setLatency(int latency) {
        this.latency = latency;

        getServer().getPlayerList().broadcastAll(getAirEntity().createPlayerInfoUpdatePacket(
                ClientboundPlayerInfoUpdatePacket.Action.UPDATE_LATENCY
        ));
    }
}
