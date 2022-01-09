package com.android.systemui.statusbar.notification.collection.coordinator;

import android.os.Parcelable;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SmartspaceDedupingCoordinator.kt */
/* synthetic */ class SmartspaceDedupingCoordinator$attach$1 implements BcSmartspaceDataPlugin.SmartspaceTargetListener {
    final /* synthetic */ SmartspaceDedupingCoordinator $tmp0;

    SmartspaceDedupingCoordinator$attach$1(SmartspaceDedupingCoordinator smartspaceDedupingCoordinator) {
        this.$tmp0 = smartspaceDedupingCoordinator;
    }

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceTargetListener
    public final void onSmartspaceTargetsUpdated(List<? extends Parcelable> list) {
        Intrinsics.checkNotNullParameter(list, "p0");
        this.$tmp0.onNewSmartspaceTargets(list);
    }
}
