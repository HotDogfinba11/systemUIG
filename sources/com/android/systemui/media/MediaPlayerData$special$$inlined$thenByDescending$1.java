package com.android.systemui.media;

import java.util.Comparator;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;

/* compiled from: Comparisons.kt */
public final class MediaPlayerData$special$$inlined$thenByDescending$1<T> implements Comparator<T> {
    final /* synthetic */ Comparator $this_thenByDescending;

    public MediaPlayerData$special$$inlined$thenByDescending$1(Comparator comparator) {
        this.$this_thenByDescending = comparator;
    }

    @Override // java.util.Comparator
    public final int compare(T t, T t2) {
        int compare = this.$this_thenByDescending.compare(t, t2);
        if (compare != 0) {
            return compare;
        }
        MediaPlayerData mediaPlayerData = MediaPlayerData.INSTANCE;
        boolean shouldPrioritizeSs$frameworks__base__packages__SystemUI__android_common__SystemUI_core = mediaPlayerData.getShouldPrioritizeSs$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
        boolean isSsMediaRec = t2.isSsMediaRec();
        if (!shouldPrioritizeSs$frameworks__base__packages__SystemUI__android_common__SystemUI_core) {
            isSsMediaRec = !isSsMediaRec;
        }
        T t3 = t;
        return ComparisonsKt__ComparisonsKt.compareValues(Boolean.valueOf(isSsMediaRec), Boolean.valueOf(mediaPlayerData.getShouldPrioritizeSs$frameworks__base__packages__SystemUI__android_common__SystemUI_core() ? t3.isSsMediaRec() : !t3.isSsMediaRec()));
    }
}
