package kotlinx.coroutines.channels;

/* compiled from: AbstractChannel.kt */
public interface ReceiveOrClosed<E> {
    void completeResumeReceive(Object obj);

    Object getOfferResult();

    Object tryResumeReceive(E e, Object obj);
}
