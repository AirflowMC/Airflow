package me.glicz.airflow.item.component.adapter;

import net.minecraft.util.Unit;

import java.util.function.Function;

public class NonValuedAdapter extends ItemComponentAdapter<Unit, Unit> {
    public NonValuedAdapter() {
        super(Function.identity(), Function.identity());
    }

    @Override
    public Unit asAir(Unit value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Unit asMinecraft(Unit value) {
        throw new UnsupportedOperationException();
    }
}
