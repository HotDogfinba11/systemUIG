package com.android.keyguard;

import java.util.Comparator;
import kotlin.comparisons.ComparisonsKt;

/* compiled from: Comparisons.kt */
public final class FontInterpolator$lerp$$inlined$sortBy$2<T> implements Comparator<T> {
    @Override // java.util.Comparator
    public final int compare(T t, T t2) {
        return ComparisonsKt.compareValues(t.getTag(), t2.getTag());
    }
}
