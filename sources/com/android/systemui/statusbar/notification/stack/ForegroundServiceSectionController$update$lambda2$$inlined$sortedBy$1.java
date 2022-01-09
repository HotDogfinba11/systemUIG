package com.android.systemui.statusbar.notification.stack;

import java.util.Comparator;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;

/* renamed from: com.android.systemui.statusbar.notification.stack.ForegroundServiceSectionController$update$lambda-2$$inlined$sortedBy$1  reason: invalid class name */
/* compiled from: Comparisons.kt */
public final class ForegroundServiceSectionController$update$lambda2$$inlined$sortedBy$1<T> implements Comparator<T> {
    @Override // java.util.Comparator
    public final int compare(T t, T t2) {
        return ComparisonsKt__ComparisonsKt.compareValues(Integer.valueOf(t.getRanking().getRank()), Integer.valueOf(t2.getRanking().getRank()));
    }
}
