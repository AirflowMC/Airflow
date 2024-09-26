package me.glicz.airflow.api.generator.inventory.menu;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import me.glicz.airflow.api.generator.FieldBasedGenerator;
import me.glicz.airflow.api.inventory.menu.MenuTypeProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

import javax.lang.model.element.Modifier;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.stream.Collectors;

public class MenuTypesGenerator extends FieldBasedGenerator {
    private static final String BASE_PACKAGE = "me.glicz.airflow.api.inventory.menu";

    public MenuTypesGenerator() {
        super(MenuType.class, BASE_PACKAGE, "MenuTypes", MenuTypeProvider.class);
    }

    @Override
    protected FieldSpec createField(Field field) throws IllegalAccessException, NoSuchMethodException {
        MenuType<?> menuType = (MenuType<?>) field.get(null);
        ResourceLocation key = BuiltInRegistries.MENU.getKey(menuType);

        return FieldSpec
                .builder(getEntryType(field), field.getName())
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .addJavadoc(ENTRY_DOC, key)
                .initializer("provider().get(key($S))", key)
                .build();
    }

    protected TypeName getEntryType(Field field) throws NoSuchMethodException {
        //noinspection unchecked
        Class<? extends AbstractContainerMenu> containerMenu = (Class<? extends AbstractContainerMenu>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];

        Class<?> airInventoryMenu = containerMenu.getMethod("getAirMenuView").getReturnType();

        String packageSuffix = airInventoryMenu.getPackageName().replace("me.glicz.airflow.inventory.menu", "");
        if (packageSuffix.startsWith(".")) {
            packageSuffix = packageSuffix.substring(1);
        }

        String[] parts = {
                BASE_PACKAGE,
                packageSuffix,
                airInventoryMenu.getSimpleName().substring(3)
        };

        String classPath = Arrays.stream(parts)
                .filter(part -> !part.isEmpty())
                .collect(Collectors.joining("."));

        String[] split = classPath.split("\\.");
        String packageName = String.join(".", Arrays.copyOfRange(split, 0, split.length - 1));
        String className = split[split.length - 1];

        return ParameterizedTypeName.get(ClassName.get(me.glicz.airflow.api.inventory.menu.MenuType.class), ClassName.get(packageName, className));
    }
}
