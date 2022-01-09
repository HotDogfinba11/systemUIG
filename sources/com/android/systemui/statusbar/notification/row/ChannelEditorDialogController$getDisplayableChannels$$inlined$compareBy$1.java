package com.android.systemui.statusbar.notification.row;

import java.util.Comparator;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;

/* compiled from: Comparisons.kt */
public final class ChannelEditorDialogController$getDisplayableChannels$$inlined$compareBy$1<T> implements Comparator<T> {
    @Override // java.util.Comparator
    public final int compare(T t, T t2) {
        T t3 = t;
        CharSequence name = t3.getName();
        String str = null;
        String obj = name == null ? null : name.toString();
        if (obj == null) {
            obj = t3.getId();
        }
        T t4 = t2;
        CharSequence name2 = t4.getName();
        if (name2 != null) {
            str = name2.toString();
        }
        if (str == null) {
            str = t4.getId();
        }
        return ComparisonsKt__ComparisonsKt.compareValues(obj, str);
    }
}
