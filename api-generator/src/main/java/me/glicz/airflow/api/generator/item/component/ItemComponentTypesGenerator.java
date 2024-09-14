package me.glicz.airflow.api.generator.item.component;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import me.glicz.airflow.api.generator.FieldBasedGenerator;
import me.glicz.airflow.api.item.component.ItemComponentType;
import me.glicz.airflow.api.item.component.ItemComponentTypeProvider;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import javax.lang.model.element.Modifier;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class ItemComponentTypesGenerator extends FieldBasedGenerator {
    private static final String BASE_PACKAGE = "me.glicz.airflow.api.item";
    private static final Map<String, String> PACKAGE_MAPPING = Map.of(
            "world.item.component", ""
    );
    private static final Map<String, String> TYPE_MAPPING = Map.of(
            "ItemLore", "lore.ItemLore"
    );

    public ItemComponentTypesGenerator() {
        super(DataComponents.class, DataComponentType.class, BASE_PACKAGE + ".component", "ItemComponentTypes", ItemComponentTypeProvider.class);
    }

    @Override
    protected FieldSpec createField(Field field) throws IllegalAccessException, ClassNotFoundException {
        DataComponentType<?> componentType = (DataComponentType<?>) field.get(null);
        ResourceLocation key = BuiltInRegistries.DATA_COMPONENT_TYPE.getKey(componentType);

        TypeName entryType = getEntryType(field);
        if (entryType == null) {
            throw new IllegalAccessException();
        }

        String initializer = "provider().getValued(key($S))";
        if (entryType instanceof ClassName className && className.simpleName().equals("NonValued")) {
            initializer = "provider().getNonValued(key($S))";
        }

        return FieldSpec
                .builder(entryType, field.getName())
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .addJavadoc(ENTRY_DOC, key)
                .initializer(initializer, key)
                .build();
    }

    protected TypeName getEntryType(Field field) throws ClassNotFoundException {
        Class<?> type = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];

        if (type.getSimpleName().equals("Unit")) {
            return ClassName.get(ItemComponentType.NonValued.class);
        }

        TypeName typeName;
        if (type.getPackageName().startsWith("java")) {
            typeName = ClassName.get(type);
        } else if (type == net.minecraft.network.chat.Component.class) {
            typeName = ClassName.get(net.kyori.adventure.text.Component.class);
        } else {

            String packageSuffix = type.getPackageName().replace("net.minecraft", "");
            if (packageSuffix.startsWith(".")) {
                packageSuffix = packageSuffix.substring(1);
            }

            String[] parts = {
                    BASE_PACKAGE,
                    PACKAGE_MAPPING.getOrDefault(packageSuffix, packageSuffix),
                    TYPE_MAPPING.getOrDefault(type.getSimpleName(), type.getSimpleName())
            };

            String classPath = Arrays.stream(parts)
                    .filter(part -> !part.isEmpty())
                    .collect(Collectors.joining("."));

            Class.forName(classPath);

            String[] split = classPath.split("\\.");
            String packageName = String.join(".", Arrays.copyOfRange(split, 0, split.length - 1));
            String className = split[split.length - 1];

            typeName = ClassName.get(packageName, className);
        }

        return ParameterizedTypeName.get(ClassName.get(ItemComponentType.Valued.class), typeName);
    }
}
