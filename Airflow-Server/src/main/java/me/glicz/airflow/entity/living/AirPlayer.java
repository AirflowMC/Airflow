package me.glicz.airflow.entity.living;

import me.glicz.airflow.api.entity.living.Player;
import me.glicz.airflow.api.inventory.menu.MenuType;
import me.glicz.airflow.api.inventory.menu.view.MenuView;
import me.glicz.airflow.inventory.menu.AirMenuType;
import net.kyori.adventure.chat.SignedMessage;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.TitlePart;
import net.kyori.adventure.util.Ticks;
import net.minecraft.network.chat.MessageSignature;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Consumer;

public class AirPlayer extends AirHumanoid implements Player {
    public AirPlayer(ServerPlayer handle) {
        super(handle);
    }

    @Override
    protected ClientboundPlayerInfoUpdatePacket createPlayerInfoUpdatePacket(ClientboundPlayerInfoUpdatePacket.Action... actions) {
        return new ClientboundPlayerInfoUpdatePacket(EnumSet.copyOf(List.of(actions)), List.of(getHandle()));
    }

    @Override
    public ServerPlayer getHandle() {
        return (ServerPlayer) super.getHandle();
    }

    @Override
    public boolean isOperator() {
        return getHandle().getServer().getPlayerList().isOp(getHandle().getGameProfile());
    }

    @Override
    public void deleteMessage(SignedMessage.@NotNull Signature signature) {
        getHandle().connection.send(new ClientboundDeleteChatPacket(new MessageSignature.Packed(new MessageSignature(signature.bytes()))));
    }

    @Override
    public void sendActionBar(@NotNull Component message) {
        getHandle().connection.send(new ClientboundSetActionBarTextPacket(componentSerializer().serialize(message)));
    }

    @Override
    public void sendPlayerListHeaderAndFooter(@NotNull Component header, @NotNull Component footer) {
        getHandle().connection.send(new ClientboundTabListPacket(
                componentSerializer().serialize(header),
                componentSerializer().serialize(footer)
        ));
    }

    @Override
    public <T> void sendTitlePart(@NotNull TitlePart<T> part, @NotNull T value) {
        if (part == TitlePart.TITLE) {
            getHandle().connection.send(new ClientboundSetTitleTextPacket(
                    componentSerializer().serialize((Component) value)
            ));
        } else if (part == TitlePart.SUBTITLE) {
            getHandle().connection.send(new ClientboundSetSubtitleTextPacket(
                    componentSerializer().serialize((Component) value)
            ));
        } else if (part == TitlePart.TIMES) {
            Title.Times times = (Title.Times) value;
            getHandle().connection.send(new ClientboundSetTitlesAnimationPacket(
                    ticks(times.fadeIn()), ticks(times.stay()), ticks(times.fadeOut())
            ));
        } else {
            throw new IllegalArgumentException("Unknown title part " + part);
        }
    }

    @Override
    public void clearTitle() {
        clearTitle(false);
    }

    @Override
    public void resetTitle() {
        clearTitle(true);
    }

    private void clearTitle(boolean resetTimes) {
        getHandle().connection.send(new ClientboundClearTitlesPacket(resetTimes));
    }

    private int ticks(Duration duration) {
        if (duration == null) {
            return -1;
        }

        return (int) (duration.getSeconds() / Ticks.SINGLE_TICK_DURATION_MS);
    }

    @Override
    public <T extends MenuView> @NotNull T openMenu(@NotNull MenuType<T> menuType, @NotNull Component title, @Nullable Consumer<T> consumer) {
        getHandle().openMenu(new SimpleMenuProvider((containerId, playerInventory, player) -> {
            AbstractContainerMenu containerMenu = ((AirMenuType<T>) menuType).handle.create(containerId, playerInventory);
            containerMenu.getAirMenuView().pluginOpened = true;

            if (consumer != null) {
                //noinspection unchecked
                consumer.accept((T) containerMenu.getAirMenuView());
            }

            return containerMenu;
        }, componentSerializer().serialize(title)));
        //noinspection unchecked
        return (T) getHandle().containerMenu.getAirMenuView();
    }
}
