package kotlinx.coroutines.internal;

import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.AbstractCoroutine;

/* compiled from: Scopes.kt */
public final class ScopesKt {
    public static final Throwable tryRecover(AbstractCoroutine<?> abstractCoroutine, Throwable th) {
        Continuation<T> continuation;
        Intrinsics.checkParameterIsNotNull(abstractCoroutine, "$this$tryRecover");
        Intrinsics.checkParameterIsNotNull(th, "exception");
        if (!(abstractCoroutine instanceof ScopeCoroutine)) {
            abstractCoroutine = null;
        }
        ScopeCoroutine scopeCoroutine = (ScopeCoroutine) abstractCoroutine;
        return (scopeCoroutine == null || (continuation = scopeCoroutine.uCont) == null) ? th : StackTraceRecoveryKt.recoverStackTrace(th, continuation);
    }
}
