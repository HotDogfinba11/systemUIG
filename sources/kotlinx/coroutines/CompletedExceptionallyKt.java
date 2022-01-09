package kotlinx.coroutines;

import kotlin.Result;
import kotlin.ResultKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CompletedExceptionally.kt */
public final class CompletedExceptionallyKt {
    public static final <T> Object toState(Object obj) {
        if (Result.m704isSuccessimpl(obj)) {
            ResultKt.throwOnFailure(obj);
            return obj;
        }
        Throwable r4 = Result.m701exceptionOrNullimpl(obj);
        if (r4 == null) {
            Intrinsics.throwNpe();
        }
        return new CompletedExceptionally(r4, false, 2, null);
    }
}
