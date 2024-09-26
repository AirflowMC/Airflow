package me.glicz.airflow.api.generator;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import net.kyori.adventure.key.Key;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;

public abstract class Generator {
    public static final String CLASS_DOC = """
            @apiNote This class was automatically generated based on internal Minecraft registries. Its content might change in future versions.
            """;
    public static final String ENTRY_DOC = """
            {@code $L}
            @apiNote This field was automatically generated based on internal Minecraft registries. It might be removed in future versions.
            """;
    private static final int INDENT_SIZE = 4;

    private final String packageName;
    private final String className;
    private final Class<?> providerType;

    public Generator(String packageName, String className, Class<?> providerType) {
        this.packageName = packageName;
        this.className = className;
        this.providerType = providerType;
    }

    public void run(String version, File sourceFolder) throws IOException {
        TypeSpec.Builder builder = TypeSpec.classBuilder(this.className)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addJavadoc(CLASS_DOC)
                .addAnnotation(AnnotationSpec.builder(GenerationVersion.class)
                        .addMember("value", "$S", version)
                        .build()
                )
                .addMethod(MethodSpec.constructorBuilder()
                        .addModifiers(Modifier.PRIVATE)
                        .build()
                );

        generateFields(builder);

        JavaFile javaFile = JavaFile.builder(this.packageName, builder.build())
                .addStaticImport(this.providerType, "provider")
                .addStaticImport(Key.class, "key")
                .indent(" ".repeat(INDENT_SIZE))
                .build();

        javaFile.writeToFile(sourceFolder);
    }

    protected abstract void generateFields(TypeSpec.Builder builder);
}
