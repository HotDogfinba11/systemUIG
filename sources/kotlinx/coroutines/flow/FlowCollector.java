package kotlinx.coroutines.flow;

import kotlin.Unit;
import kotlin.coroutines.Continuation;

/* compiled from: FlowCollector.kt */
public interface FlowCollector<T> {
    Object emit(T t, Continuation<? super Unit> continuation);
}
