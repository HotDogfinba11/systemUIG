package kotlin.sequences;

import java.util.Iterator;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: Sequences.kt */
public final class TransformingIndexedSequence$iterator$1 implements Iterator<R>, KMappedMarker {
    private int index;
    private final Iterator<T> iterator;
    final /* synthetic */ TransformingIndexedSequence this$0;

    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    /* JADX WARN: Incorrect args count in method signature: ()V */
    TransformingIndexedSequence$iterator$1(TransformingIndexedSequence transformingIndexedSequence) {
        this.this$0 = transformingIndexedSequence;
        this.iterator = transformingIndexedSequence.sequence.iterator();
    }

    @Override // java.util.Iterator
    public R next() {
        Function2 function2 = this.this$0.transformer;
        int i = this.index;
        this.index = i + 1;
        if (i < 0) {
            CollectionsKt__CollectionsKt.throwIndexOverflow();
        }
        return (R) function2.invoke(Integer.valueOf(i), this.iterator.next());
    }

    public boolean hasNext() {
        return this.iterator.hasNext();
    }
}
