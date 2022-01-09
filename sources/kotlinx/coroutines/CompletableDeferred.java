package kotlinx.coroutines;

/* compiled from: CompletableDeferred.kt */
public interface CompletableDeferred<T> extends Deferred<T> {
    boolean complete(T t);
}
