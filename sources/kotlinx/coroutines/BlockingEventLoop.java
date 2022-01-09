package kotlinx.coroutines;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: EventLoop.kt */
public final class BlockingEventLoop extends EventLoopImplBase {
    private final Thread thread;

    /* access modifiers changed from: protected */
    @Override // kotlinx.coroutines.EventLoopImplPlatform
    public Thread getThread() {
        return this.thread;
    }

    public BlockingEventLoop(Thread thread2) {
        Intrinsics.checkParameterIsNotNull(thread2, "thread");
        this.thread = thread2;
    }
}
