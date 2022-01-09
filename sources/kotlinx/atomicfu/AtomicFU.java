package kotlinx.atomicfu;

/* compiled from: AtomicFU.kt */
public final class AtomicFU {
    public static final <T> AtomicRef<T> atomic(T t) {
        return new AtomicRef<>(t);
    }
}
