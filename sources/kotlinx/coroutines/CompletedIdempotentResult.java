package kotlinx.coroutines;

import kotlin.jvm.internal.Intrinsics;

/* access modifiers changed from: package-private */
/* compiled from: CancellableContinuationImpl.kt */
public final class CompletedIdempotentResult {
    public final Object idempotentResume;
    public final Object result;
    public final NotCompleted token;

    public CompletedIdempotentResult(Object obj, Object obj2, NotCompleted notCompleted) {
        Intrinsics.checkParameterIsNotNull(notCompleted, "token");
        this.idempotentResume = obj;
        this.result = obj2;
        this.token = notCompleted;
    }

    public String toString() {
        return "CompletedIdempotentResult[" + this.result + ']';
    }
}
