package kotlinx.coroutines;

import java.util.concurrent.locks.LockSupport;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;

/* access modifiers changed from: package-private */
/* compiled from: Builders.kt */
public final class BlockingCoroutine<T> extends AbstractCoroutine<T> {
    private final Thread blockedThread;
    private final EventLoop eventLoop;

    /* access modifiers changed from: protected */
    @Override // kotlinx.coroutines.JobSupport
    public boolean isScopedCoroutine() {
        return true;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public BlockingCoroutine(CoroutineContext coroutineContext, Thread thread, EventLoop eventLoop2) {
        super(coroutineContext, true);
        Intrinsics.checkParameterIsNotNull(coroutineContext, "parentContext");
        Intrinsics.checkParameterIsNotNull(thread, "blockedThread");
        this.blockedThread = thread;
        this.eventLoop = eventLoop2;
    }

    /* access modifiers changed from: protected */
    @Override // kotlinx.coroutines.JobSupport
    public void afterCompletionInternal(Object obj, int i) {
        if (!Intrinsics.areEqual(Thread.currentThread(), this.blockedThread)) {
            LockSupport.unpark(this.blockedThread);
        }
    }

    public final T joinBlocking() {
        TimeSource timeSource = TimeSourceKt.getTimeSource();
        if (timeSource != null) {
            timeSource.registerTimeLoopThread();
        }
        try {
            EventLoop eventLoop2 = this.eventLoop;
            CompletedExceptionally completedExceptionally = null;
            if (eventLoop2 != null) {
                EventLoop.incrementUseCount$default(eventLoop2, false, 1, completedExceptionally);
            }
            while (!Thread.interrupted()) {
                try {
                    EventLoop eventLoop3 = this.eventLoop;
                    long processNextEvent = eventLoop3 != null ? eventLoop3.processNextEvent() : Long.MAX_VALUE;
                    if (isCompleted()) {
                        T t = (T) JobSupportKt.unboxState(getState$kotlinx_coroutines_core());
                        if (t instanceof CompletedExceptionally) {
                            completedExceptionally = t;
                        }
                        CompletedExceptionally completedExceptionally2 = completedExceptionally;
                        if (completedExceptionally2 == null) {
                            return t;
                        }
                        throw completedExceptionally2.cause;
                    }
                    TimeSource timeSource2 = TimeSourceKt.getTimeSource();
                    if (timeSource2 != null) {
                        timeSource2.parkNanos(this, processNextEvent);
                    } else {
                        LockSupport.parkNanos(this, processNextEvent);
                    }
                } finally {
                    EventLoop eventLoop4 = this.eventLoop;
                    if (eventLoop4 != null) {
                        EventLoop.decrementUseCount$default(eventLoop4, false, 1, completedExceptionally);
                    }
                }
            }
            InterruptedException interruptedException = new InterruptedException();
            cancelCoroutine(interruptedException);
            throw interruptedException;
        } finally {
            TimeSource timeSource3 = TimeSourceKt.getTimeSource();
            if (timeSource3 != null) {
                timeSource3.unregisterTimeLoopThread();
            }
        }
    }
}
