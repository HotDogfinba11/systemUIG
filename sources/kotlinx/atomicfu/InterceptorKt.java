package kotlinx.atomicfu;

import java.util.concurrent.locks.ReentrantLock;

/* compiled from: Interceptor.kt */
public final class InterceptorKt {
    private static AtomicOperationInterceptor interceptor = DefaultInterceptor.INSTANCE;
    private static final ReentrantLock interceptorLock = new ReentrantLock();

    public static final AtomicOperationInterceptor getInterceptor() {
        return interceptor;
    }
}
