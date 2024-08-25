package me.glicz.testplugin;

import me.glicz.airflow.api.plugin.Plugin;
import me.glicz.airflow.api.plugin.bootstrap.BootstrapContext;
import me.glicz.testplugin.event.TestEvent;
import me.glicz.testplugin.listener.TestListener;
import org.jetbrains.annotations.NotNull;

public class TestPlugin extends Plugin {
    @Override
    public void bootstrap(@NotNull BootstrapContext ctx) {
        getLogger().info("Welcome to Airflow {}!", ctx.getServerVersion().getName());
        getLogger().info("Here we are in bootstrap context where server is {}", getServer());
        getLogger().info("Also, note, that plugin uses {} classloader", getClass().getClassLoader());

        getEventBus().subscribe(TestEvent.class, e -> {
            getLogger().info("Here, in bootstrap, we're subscribing to TestEvent, however it will also dispatch during onLoad with local EventBus!");
        });
        new TestListener(this).register();

        ctx.getServerEventBus().dispatch(new TestEvent());
    }

    @Override
    public void onLoad() {
        getLogger().info("Successfully loaded!");
        getLogger().info("And here, in onLoad, the server is with us! Welcome {}", getServer());

        getEventBus().dispatch(new TestEvent());
    }

    @Override
    public void onEnable() {
        getLogger().info("Successfully enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Successfully disabled!");
    }
}
