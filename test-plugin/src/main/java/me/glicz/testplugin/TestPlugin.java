package me.glicz.testplugin;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.glicz.airflow.api.block.BlockTypes;
import me.glicz.airflow.api.command.Commands;
import me.glicz.airflow.api.event.command.CommandsRegisterEvent;
import me.glicz.airflow.api.event.packet.ItemStackEncodeEvent;
import me.glicz.airflow.api.event.player.PlayerJoinEvent;
import me.glicz.airflow.api.event.player.PlayerQuitEvent;
import me.glicz.airflow.api.permission.DummyPermissionsSource;
import me.glicz.airflow.api.permission.Permission;
import me.glicz.airflow.api.permission.PermissionSourcePriority;
import me.glicz.airflow.api.plugin.Plugin;
import me.glicz.airflow.api.plugin.bootstrap.BootstrapContext;
import me.glicz.airflow.api.service.ServicePriority;
import me.glicz.airflow.api.service.ServiceProvider;
import me.glicz.testplugin.event.TestEvent;
import me.glicz.testplugin.listener.*;
import me.glicz.testplugin.service.TestService;
import me.glicz.testplugin.service.TestServiceImpl;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

import java.util.List;

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

        ctx.getServices().register(TestService.class, new TestServiceImpl(this), this, ServicePriority.NORMAL);

        getEventBus().subscribe(CommandsRegisterEvent.class, e -> {
            getLogger().info("Hello CommandsRegisterEvent!");
            e.getCommands().register(
                    this,
                    Commands.literal("test")
                            .then(Commands.literal("airflow")
                                    .executes(context -> {
                                        context.getSource().getSender().sendMessage("This server runs Airflow!");
                                        return 1;
                                    })
                            )
                            .then(Commands.literal("op")
                                    .requires(source -> source.getSender().hasPermission(Key.key(this, "nice_permission")))
                                    .executes(context -> {
                                        context.getSource().getSender().sendMessage("This command tests permission that is default for operators :)");
                                        return 1;
                                    })
                            )
                            .executes(context -> {
                                context.getSource().getSender().sendMessage("Hello!");
                                return 1;
                            })
                            .build(),
                    null,
                    List.of()
            );
            e.getCommands().register(
                    this,
                    Commands.literal("say")
                            .then(Commands.argument("message", StringArgumentType.greedyString())
                                    .executes(context -> {
                                        String message = context.getArgument("message", String.class);
                                        context.getSource().getSender().sendMessage("Overriden! " + message);
                                        return 1;
                                    })
                            )
                            .build(),
                    null,
                    List.of()
            );
        });
    }

    @Override
    public void onLoad() {
        getEventBus().dispatch(new TestEvent());

        getServer().getServices().get(TestService.class)
                .map(ServiceProvider::getProvider)
                .ifPresent(TestService::helloWorld);

        getLogger().info("Successfully loaded!");
        getLogger().info("And here, in onLoad, the server is with us! Welcome {}", getServer());
    }

    @Override
    public void onEnable() {
        getEventBus().subscribe(ItemStackEncodeEvent.class, new EmeraldEncodeListener());
        getEventBus().subscribe(PlayerJoinEvent.class, new JoinListener(this));
        getEventBus().subscribe(PlayerQuitEvent.class, new QuitListener());

        new InventoryListener(this).register();

        getServer().getPermissions().registerPermission(this, "nice_permission", Permission.DefaultValue.OP);

        getScheduler()
                .taskBuilder(() -> {
                    getLogger().info("Delayed async task! Current thread: {}", Thread.currentThread());
                })
                .async()
                .delay(40)
                .schedule();

        getScheduler()
                .repeatingTaskBuilder(() -> {
                    getLogger().info("This task runs every 5 minutes without any start delay!");
                })
                .interval(5 * 60 * 20)
                .schedule();

        getLogger().info("Successfully enabled!");
        getLogger().warn("This is a warning!");
        getLogger().error("This is an error!");

        DummyPermissionsSource permissionsSource1 = new DummyPermissionsSource(getServer().getServerCommandSender());
        permissionsSource1.addPermission(Key.key("air", "flow"), true);
        permissionsSource1.addPermission(Key.key("hello", "world"), false);

        DummyPermissionsSource permissionsSource2 = new DummyPermissionsSource(getServer().getServerCommandSender());
        permissionsSource2.addPermissionsSource(PermissionSourcePriority.NORMAL, permissionsSource1);
        permissionsSource2.addPermission(Key.key("permission", "test"), true);
        getServer().getServerCommandSender().addPermissionsSource(
                PermissionSourcePriority.NORMAL,
                permissionsSource2
        );

        DummyPermissionsSource permissionsSource3 = new DummyPermissionsSource(getServer().getServerCommandSender());
        permissionsSource3.addPermission(Key.key("hello", "world"), true);
        permissionsSource3.addPermission(Key.key("permission", "test"), false);
        getServer().getServerCommandSender().addPermissionsSource(
                PermissionSourcePriority.HIGH,
                permissionsSource3
        );

        getLogger().info(getServer().getServerCommandSender().getPermissions().toString());

        try {
            getLogger().info(BlockTypes.CHEST.createBlockState("[waterlogged=true]").toString());
        } catch (CommandSyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("Successfully disabled!");
    }
}
