package me.glicz.airflow.api.inventory.menu.view;

import org.jetbrains.annotations.Nullable;

public interface AnvilView extends ItemCombinerView {
    @Nullable String getInputText();
}
