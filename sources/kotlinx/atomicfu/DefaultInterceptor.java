package kotlinx.atomicfu;

/* compiled from: Interceptor.kt */
final class DefaultInterceptor extends AtomicOperationInterceptor {
    public static final DefaultInterceptor INSTANCE = new DefaultInterceptor();

    public String toString() {
        return "DefaultInterceptor";
    }

    private DefaultInterceptor() {
    }
}
