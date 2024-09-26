package me.glicz.airflow.inventory.menu;

import me.glicz.airflow.api.inventory.menu.view.MenuView;
import net.kyori.adventure.key.Key;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.NotNull;

public class AirMenuType<T extends MenuView> implements me.glicz.airflow.api.inventory.menu.MenuType<T> {
    public final MenuType<?> handle;

    public AirMenuType(MenuType<?> handle) {
        this.handle = handle;
    }

    @SuppressWarnings({"PatternValidation", "DataFlowIssue"})
    @Override
    public @NotNull Key key() {
        return Key.key(BuiltInRegistries.MENU.getKey(this.handle).toString());
    }
}
