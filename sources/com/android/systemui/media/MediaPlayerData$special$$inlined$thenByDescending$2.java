package com.android.systemui.media;

import java.util.Comparator;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;

/* compiled from: Comparisons.kt */
public final class MediaPlayerData$special$$inlined$thenByDescending$2<T> implements Comparator<T> {
    final /* synthetic */ Comparator $this_thenByDescending;

    public MediaPlayerData$special$$inlined$thenByDescending$2(Comparator comparator) {
        this.$this_thenByDescending = comparator;
    }

    @Override // java.util.Comparator
    public final int compare(T t, T t2) {
        int compare = this.$this_thenByDescending.compare(t, t2);
        return compare != 0 ? compare : ComparisonsKt__ComparisonsKt.compareValues(Boolean.valueOf(t2.getData().isLocalSession()), Boolean.valueOf(t.getData().isLocalSession()));
    }
}
