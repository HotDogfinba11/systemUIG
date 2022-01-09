package kotlinx.coroutines.internal;

import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;

/* access modifiers changed from: package-private */
/* compiled from: ThreadContext.kt */
public final class ThreadState {
    private Object[] a;
    private final CoroutineContext context;
    private int i;

    public ThreadState(CoroutineContext coroutineContext, int i2) {
        Intrinsics.checkParameterIsNotNull(coroutineContext, "context");
        this.context = coroutineContext;
        this.a = new Object[i2];
    }

    public final CoroutineContext getContext() {
        return this.context;
    }

    public final void append(Object obj) {
        Object[] objArr = this.a;
        int i2 = this.i;
        this.i = i2 + 1;
        objArr[i2] = obj;
    }

    public final Object take() {
        Object[] objArr = this.a;
        int i2 = this.i;
        this.i = i2 + 1;
        return objArr[i2];
    }

    public final void start() {
        this.i = 0;
    }
}
