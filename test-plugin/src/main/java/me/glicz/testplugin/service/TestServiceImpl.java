package me.glicz.testplugin.service;

import me.glicz.testplugin.TestPlugin;

public class TestServiceImpl implements TestService {
    private final TestPlugin plugin;

    public TestServiceImpl(TestPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void helloWorld() {
        this.plugin.getLogger().info("Hello, World! Services work");
    }
}
