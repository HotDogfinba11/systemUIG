package kotlinx.coroutines.flow.internal;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.internal.ScopeCoroutine;

/* compiled from: SafeCollector.kt */
public final class SafeCollector<T> implements FlowCollector<T> {
    private final CoroutineContext collectContext;
    private final int collectContextSize;
    private final FlowCollector<T> collector;
    private CoroutineContext lastEmissionContext;

    /* JADX DEBUG: Multi-variable search result rejected for r2v0, resolved type: kotlinx.coroutines.flow.FlowCollector<? super T> */
    /* JADX WARN: Multi-variable type inference failed */
    public SafeCollector(FlowCollector<? super T> flowCollector, CoroutineContext coroutineContext) {
        Intrinsics.checkParameterIsNotNull(flowCollector, "collector");
        Intrinsics.checkParameterIsNotNull(coroutineContext, "collectContext");
        this.collector = flowCollector;
        this.collectContext = coroutineContext;
        this.collectContextSize = ((Number) coroutineContext.fold(0, SafeCollector$collectContextSize$1.INSTANCE)).intValue();
    }

    @Override // kotlinx.coroutines.flow.FlowCollector
    public Object emit(T t, Continuation<? super Unit> continuation) {
        CoroutineContext context = continuation.getContext();
        if (this.lastEmissionContext != context) {
            checkContext(context);
            this.lastEmissionContext = context;
        }
        return this.collector.emit(t, continuation);
    }

    private final void checkContext(CoroutineContext coroutineContext) {
        if (((Number) coroutineContext.fold(0, new SafeCollector$checkContext$result$1(this))).intValue() != this.collectContextSize) {
            throw new IllegalStateException(("Flow invariant is violated:\n" + "\t\tFlow was collected in " + this.collectContext + ",\n" + "\t\tbut emission happened in " + coroutineContext + ".\n" + "\t\tPlease refer to 'flow' documentation or use 'flowOn' instead").toString());
        }
    }

    /* access modifiers changed from: private */
    public final Job transitiveCoroutineParent(Job job, Job job2) {
        while (job != null) {
            if (job == job2 || !(job instanceof ScopeCoroutine)) {
                return job;
            }
            job = ((ScopeCoroutine) job).getParent$kotlinx_coroutines_core();
        }
        return null;
    }
}
