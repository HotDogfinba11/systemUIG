package kotlin.collections;

import java.util.Iterator;

/* compiled from: Grouping.kt */
public interface Grouping<T, K> {
    K keyOf(T t);

    Iterator<T> sourceIterator();
}
