package me.glicz.airflow.api.generator.block.state;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import me.glicz.airflow.api.block.state.BlockStateProperty;
import me.glicz.airflow.api.block.state.BlockStatePropertyProvider;
import me.glicz.airflow.api.generator.FieldBasedGenerator;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;

import javax.lang.model.element.Modifier;
import java.lang.reflect.Field;

public class BlockStatePropertiesGenerator extends FieldBasedGenerator {
    private static final String BASE_PACKAGE = "me.glicz.airflow.api.block.state";

    public BlockStatePropertiesGenerator() {
        super(BlockStateProperties.class, Property.class, BASE_PACKAGE, "BlockStateProperties", BlockStatePropertyProvider.class);
    }

    @Override
    protected FieldSpec createField(Field field) throws IllegalAccessException {
        ClassName entryType = getEntryType(field);
        if (entryType == null) {
            throw new IllegalAccessException();
        }

        String initializer = switch (entryType.simpleName()) {
            case "Boolean" -> "provider().getBoolean($S)";
            case "Integer" -> "provider().getInteger($S)";
            default -> throw new IllegalAccessException();
        };

        Property<?> property = (Property<?>) field.get(null);

        return FieldSpec
                .builder(entryType, field.getName())
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .addJavadoc(ENTRY_DOC, property.getName())
                .initializer(initializer, field.getName())
                .build();
    }


    protected ClassName getEntryType(Field field) {
        Class<?> type = field.getType();
        if (type == BooleanProperty.class) {
            return ClassName.get(BlockStateProperty.Boolean.class);
        } else if (type == IntegerProperty.class) {
            return ClassName.get(BlockStateProperty.Integer.class);
        }

        return null;
    }
}
