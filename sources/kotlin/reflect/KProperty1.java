package kotlin.reflect;

import kotlin.jvm.functions.Function1;

/* compiled from: KProperty.kt */
public interface KProperty1<T, V> extends KProperty<V>, Function1<T, V> {

    /* compiled from: KProperty.kt */
    public interface Getter<T, V> extends Function1<T, V>, Function1 {
    }

    V get(T t);

    Getter<T, V> getGetter();
}
