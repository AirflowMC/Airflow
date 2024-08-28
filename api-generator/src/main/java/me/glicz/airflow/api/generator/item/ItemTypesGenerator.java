package me.glicz.airflow.api.generator.item;

import com.squareup.javapoet.*;
import me.glicz.airflow.api.generator.GenerationVersion;
import me.glicz.airflow.api.item.ItemType;
import me.glicz.airflow.api.item.ItemTypeProvider;
import net.kyori.adventure.key.Key;
import net.minecraft.core.registries.BuiltInRegistries;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class ItemTypesGenerator {
    private static final String ITEM_TYPE_DOC = """
            {@code $L}
            @apiNote This field was automatically generated based on internal Minecraft registry. It might be removed in future versions.
            """;

    public static void run(String version, File sourceFolder) throws IOException {
        TypeSpec.Builder itemTypes = TypeSpec.classBuilder("ItemTypes")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addAnnotation(AnnotationSpec.builder(GenerationVersion.class)
                        .addMember("value", "$S", version)
                        .build()
                )
                .addMethod(MethodSpec.constructorBuilder()
                        .addModifiers(Modifier.PRIVATE)
                        .build()
                );

        BuiltInRegistries.ITEM.keySet().stream()
                .sorted()
                .forEach(key -> itemTypes.addField(FieldSpec
                        .builder(ItemType.class, key.getPath().toUpperCase(Locale.ENGLISH))
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                        .addJavadoc(ITEM_TYPE_DOC, key)
                        .initializer("provider().get(key($S))", key)
                        .build()
                ));

        JavaFile javaFile = JavaFile.builder("me.glicz.airflow.api.item", itemTypes.build())
                .addStaticImport(ItemTypeProvider.class, "provider")
                .addStaticImport(Key.class, "key")
                .build();

        javaFile.writeToFile(sourceFolder);
    }
}
