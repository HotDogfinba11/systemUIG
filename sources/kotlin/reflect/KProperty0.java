package kotlin.reflect;

import kotlin.jvm.functions.Function0;

/* compiled from: KProperty.kt */
public interface KProperty0<V> extends KProperty<V>, Function0<V> {

    /* compiled from: KProperty.kt */
    public interface Getter<V> extends Function0<V>, Function0 {
    }

    V get();

    Getter<V> getGetter();
}
