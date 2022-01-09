package kotlinx.coroutines.flow.internal;

import kotlin.Unit;
import kotlin.coroutines.Continuation;

/* compiled from: AbstractSharedFlow.kt */
public abstract class AbstractSharedFlowSlot<F> {
    public abstract boolean allocateLocked(F f);

    public abstract Continuation<Unit>[] freeLocked(F f);
}
