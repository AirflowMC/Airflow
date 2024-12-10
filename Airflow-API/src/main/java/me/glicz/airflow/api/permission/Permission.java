package me.glicz.airflow.api.permission;

import com.google.common.base.Predicates;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public interface Permission extends Keyed {
    @NotNull DefaultValue getDefaultValue();

    enum DefaultValue {
        FALSE(Predicates.alwaysFalse()),
        OP(holder -> holder instanceof Operator operator && operator.isOperator()),
        TRUE(Predicates.alwaysTrue());

        private final Predicate<PermissionsHolder> predicate;

        DefaultValue(Predicate<PermissionsHolder> predicate) {
            this.predicate = predicate;
        }

        public boolean test(@NotNull PermissionsHolder sender) {
            return predicate.test(sender);
        }
    }
}
