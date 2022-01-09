package kotlinx.coroutines.flow;

/* compiled from: SharedFlow.kt */
public interface MutableSharedFlow<T> extends FlowCollector<T>, FlowCollector {
    boolean tryEmit(T t);
}
