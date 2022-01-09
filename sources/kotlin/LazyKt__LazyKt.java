package kotlin;

/* access modifiers changed from: package-private */
/* compiled from: Lazy.kt */
public class LazyKt__LazyKt extends LazyKt__LazyJVMKt {
    public static <T> Lazy<T> lazyOf(T t) {
        return new InitializedLazyImpl(t);
    }
}
