package com.android.systemui.media;

import java.util.Comparator;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;

/* compiled from: Comparisons.kt */
public final class MediaPlayerData$special$$inlined$compareByDescending$1<T> implements Comparator<T> {
    @Override // java.util.Comparator
    public final int compare(T t, T t2) {
        return ComparisonsKt__ComparisonsKt.compareValues(t2.getData().isPlaying(), t.getData().isPlaying());
    }
}
