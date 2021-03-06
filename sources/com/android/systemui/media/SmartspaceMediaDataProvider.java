package com.android.systemui.media;

import android.app.smartspace.SmartspaceTarget;
import android.util.Log;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;

/* compiled from: SmartspaceMediaDataProvider.kt */
public final class SmartspaceMediaDataProvider implements BcSmartspaceDataPlugin {
    private final List<BcSmartspaceDataPlugin.SmartspaceTargetListener> smartspaceMediaTargetListeners = new ArrayList();
    private List<SmartspaceTarget> smartspaceMediaTargets = CollectionsKt__CollectionsKt.emptyList();

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin
    public void registerListener(BcSmartspaceDataPlugin.SmartspaceTargetListener smartspaceTargetListener) {
        Intrinsics.checkNotNullParameter(smartspaceTargetListener, "smartspaceTargetListener");
        this.smartspaceMediaTargetListeners.add(smartspaceTargetListener);
    }

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin
    public void unregisterListener(BcSmartspaceDataPlugin.SmartspaceTargetListener smartspaceTargetListener) {
        List<BcSmartspaceDataPlugin.SmartspaceTargetListener> list = this.smartspaceMediaTargetListeners;
        Objects.requireNonNull(list, "null cannot be cast to non-null type kotlin.collections.MutableCollection<T>");
        TypeIntrinsics.asMutableCollection(list).remove(smartspaceTargetListener);
    }

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin
    public void onTargetsAvailable(List<SmartspaceTarget> list) {
        Intrinsics.checkNotNullParameter(list, "targets");
        ArrayList arrayList = new ArrayList();
        for (SmartspaceTarget smartspaceTarget : list) {
            if (smartspaceTarget.getFeatureType() == 15) {
                arrayList.add(smartspaceTarget);
            }
        }
        if (!arrayList.isEmpty()) {
            Log.d("SsMediaDataProvider", Intrinsics.stringPlus("Forwarding Smartspace media updates ", arrayList));
        }
        this.smartspaceMediaTargets = arrayList;
        Iterator<T> it = this.smartspaceMediaTargetListeners.iterator();
        while (it.hasNext()) {
            it.next().onSmartspaceTargetsUpdated(this.smartspaceMediaTargets);
        }
    }
}
