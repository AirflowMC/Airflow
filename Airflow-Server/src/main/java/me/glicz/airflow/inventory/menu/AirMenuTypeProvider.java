package me.glicz.airflow.inventory.menu;

import me.glicz.airflow.api.inventory.menu.MenuType;
import me.glicz.airflow.api.inventory.menu.MenuTypeProvider;
import me.glicz.airflow.api.inventory.menu.view.MenuView;
import net.kyori.adventure.key.Key;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class AirMenuTypeProvider extends MenuTypeProvider {
    @Override
    protected <T extends MenuView> MenuType<T> get(Key key) {
        //noinspection unchecked,DataFlowIssue
        return (MenuType<T>) BuiltInRegistries.MENU.getValue(ResourceLocation.parse(key.asString())).airMenuType;
    }
}
