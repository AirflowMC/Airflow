package me.glicz.airflow.api.permission;

import com.google.common.base.Predicates;
import me.glicz.airflow.api.command.sender.CommandSender;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public interface Permission extends Keyed {
    @NotNull DefaultValue getDefaultValue();

    enum DefaultValue {
        FALSE(Predicates.alwaysFalse()),
        OP(CommandSender::isOperator),
        TRUE(Predicates.alwaysTrue());

        private final Predicate<CommandSender> predicate;

        DefaultValue(Predicate<CommandSender> predicate) {
            this.predicate = predicate;
        }

        public boolean test(@NotNull CommandSender sender) {
            return predicate.test(sender);
        }
    }
}
