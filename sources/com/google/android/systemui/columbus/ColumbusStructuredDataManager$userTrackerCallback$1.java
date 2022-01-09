package com.google.android.systemui.columbus;

import android.content.Context;
import com.android.systemui.settings.UserTracker;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ColumbusStructuredDataManager.kt */
public final class ColumbusStructuredDataManager$userTrackerCallback$1 implements UserTracker.Callback {
    final /* synthetic */ ColumbusStructuredDataManager this$0;

    ColumbusStructuredDataManager$userTrackerCallback$1(ColumbusStructuredDataManager columbusStructuredDataManager) {
        this.this$0 = columbusStructuredDataManager;
    }

    @Override // com.android.systemui.settings.UserTracker.Callback
    public void onUserChanged(int i, Context context) {
        Intrinsics.checkNotNullParameter(context, "userContext");
        Object obj = this.this$0.lock;
        ColumbusStructuredDataManager columbusStructuredDataManager = this.this$0;
        synchronized (obj) {
            columbusStructuredDataManager.packageStats = columbusStructuredDataManager.fetchPackageStats();
            Unit unit = Unit.INSTANCE;
        }
    }
}
