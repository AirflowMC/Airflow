package me.glicz.testplugin.listener;

import me.glicz.airflow.api.event.bus.EventHandler;
import me.glicz.testplugin.TestPlugin;
import me.glicz.testplugin.event.TestEvent;

public class TestListener {
    private final TestPlugin plugin;
    private EventHandler<TestEvent> handler;

    public TestListener(TestPlugin plugin) {
        this.plugin = plugin;
    }

    public void register() {
        this.handler = this.plugin.getEventBus().subscribe(TestEvent.class, this::onTestEvent);
    }

    public void onTestEvent(TestEvent e) {
        this.plugin.getLogger().info("We also subscribed to TestEvent in additional listener class, which we then unregister");
        this.plugin.getEventBus().unsubscribe(TestEvent.class, this.handler);
    }
}
