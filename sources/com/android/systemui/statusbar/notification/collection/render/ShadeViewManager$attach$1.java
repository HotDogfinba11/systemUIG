package com.android.systemui.statusbar.notification.collection.render;

import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.ShadeListBuilder;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* access modifiers changed from: package-private */
/* compiled from: ShadeViewManager.kt */
public /* synthetic */ class ShadeViewManager$attach$1 implements ShadeListBuilder.OnRenderListListener {
    final /* synthetic */ ShadeViewManager $tmp0;

    ShadeViewManager$attach$1(ShadeViewManager shadeViewManager) {
        this.$tmp0 = shadeViewManager;
    }

    @Override // com.android.systemui.statusbar.notification.collection.ShadeListBuilder.OnRenderListListener
    public final void onRenderList(List<? extends ListEntry> list) {
        Intrinsics.checkNotNullParameter(list, "p0");
        this.$tmp0.onNewNotifTree(list);
    }
}
