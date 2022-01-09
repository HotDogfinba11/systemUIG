package kotlinx.coroutines;

import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;

/* access modifiers changed from: package-private */
/* compiled from: Builders.common.kt */
public class StandaloneCoroutine extends AbstractCoroutine<Unit> {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public StandaloneCoroutine(CoroutineContext coroutineContext, boolean z) {
        super(coroutineContext, z);
        Intrinsics.checkParameterIsNotNull(coroutineContext, "parentContext");
    }

    /* access modifiers changed from: protected */
    @Override // kotlinx.coroutines.JobSupport
    public boolean handleJobException(Throwable th) {
        Intrinsics.checkParameterIsNotNull(th, "exception");
        CoroutineExceptionHandlerKt.handleCoroutineException(getContext(), th);
        return true;
    }
}
