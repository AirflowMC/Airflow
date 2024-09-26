package me.glicz.airflow.api.inventory.menu;

import me.glicz.airflow.api.inventory.menu.view.MenuView;
import me.glicz.airflow.api.util.LazyReference;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.ApiStatus;

import static me.glicz.airflow.api.util.ServiceUtils.lazyLoadService;

@ApiStatus.Internal
public abstract class MenuTypeProvider {
    private static final LazyReference<MenuTypeProvider> INSTANCE = lazyLoadService(MenuTypeProvider.class);

    static MenuTypeProvider provider() {
        return INSTANCE.get();
    }

    protected abstract <T extends MenuView> MenuType<T> get(Key key);
}
