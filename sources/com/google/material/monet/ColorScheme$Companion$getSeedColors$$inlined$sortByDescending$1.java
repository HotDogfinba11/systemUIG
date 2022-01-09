package com.google.material.monet;

import java.util.Comparator;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;

/* compiled from: Comparisons.kt */
public final class ColorScheme$Companion$getSeedColors$$inlined$sortByDescending$1<T> implements Comparator<T> {
    @Override // java.util.Comparator
    public final int compare(T t, T t2) {
        return ComparisonsKt__ComparisonsKt.compareValues((Double) t2.getValue(), (Double) t.getValue());
    }
}
