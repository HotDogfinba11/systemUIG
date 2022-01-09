package kotlinx.coroutines.channels;

import kotlin.Unit;
import kotlin.coroutines.Continuation;

/* compiled from: Channel.kt */
public interface SendChannel<E> {
    Object send(E e, Continuation<? super Unit> continuation);
}
