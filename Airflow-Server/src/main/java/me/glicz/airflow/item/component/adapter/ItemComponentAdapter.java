package me.glicz.airflow.item.component.adapter;

import java.util.function.Function;

public class ItemComponentAdapter<M, A> {
    private final Function<M, A> asAir;
    private final Function<A, M> asMinecraft;

    public ItemComponentAdapter(Function<M, A> asAir, Function<A, M> asMinecraft) {
        this.asAir = asAir;
        this.asMinecraft = asMinecraft;
    }

    public A asAir(M value) {
        if (value == null) return null;

        return asAir.apply(value);
    }

    public M asMinecraft(A value) {
        if (value == null) return null;

        return asMinecraft.apply(value);
    }
}
