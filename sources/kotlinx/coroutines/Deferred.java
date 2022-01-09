package kotlinx.coroutines;

import kotlin.coroutines.Continuation;

/* compiled from: Deferred.kt */
public interface Deferred<T> extends Job {
    Object await(Continuation<? super T> continuation);
}
