package me.glicz.airflow.api.generator.entity;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import me.glicz.airflow.api.entity.EntityTypeProvider;
import me.glicz.airflow.api.generator.FieldBasedGenerator;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

import javax.lang.model.element.Modifier;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.stream.Collectors;

public class EntityTypesGenerator extends FieldBasedGenerator {
    private static final String BASE_PACKAGE = "me.glicz.airflow.api.entity";

    public EntityTypesGenerator() {
        super(EntityType.class, BASE_PACKAGE, "EntityTypes", EntityTypeProvider.class);
    }

    @Override
    protected FieldSpec createField(Field field) throws IllegalAccessException, NoSuchMethodException {
        EntityType<?> entityType = (EntityType<?>) field.get(null);
        ResourceLocation key = BuiltInRegistries.ENTITY_TYPE.getKey(entityType);

        return FieldSpec
                .builder(getEntryType(field), field.getName())
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .addJavadoc(ENTRY_DOC, key)
                .initializer("provider().get(key($S))", key)
                .build();
    }

    protected TypeName getEntryType(Field field) throws NoSuchMethodException {
        //noinspection unchecked
        Class<? extends Entity> entity = (Class<? extends Entity>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];

        TypeName typeName;
        if (entity == net.minecraft.world.entity.player.Player.class) {
            typeName = ClassName.get(me.glicz.airflow.api.entity.living.Player.class);
        } else {
            Class<?> airEntity = entity.getMethod("getAirEntity").getReturnType();

            String packageSuffix = airEntity.getPackageName().replace("me.glicz.airflow.entity", "");
            if (packageSuffix.startsWith(".")) {
                packageSuffix = packageSuffix.substring(1);
            }

            String[] parts = {
                    BASE_PACKAGE,
                    packageSuffix,
                    airEntity.getSimpleName().substring(3)
            };

            String classPath = Arrays.stream(parts)
                    .filter(part -> !part.isEmpty())
                    .collect(Collectors.joining("."));

            String[] split = classPath.split("\\.");
            String packageName = String.join(".", Arrays.copyOfRange(split, 0, split.length - 1));
            String className = split[split.length - 1];

            typeName = ClassName.get(packageName, className);
        }

        return ParameterizedTypeName.get(ClassName.get(me.glicz.airflow.api.entity.EntityType.class), typeName);
    }
}
