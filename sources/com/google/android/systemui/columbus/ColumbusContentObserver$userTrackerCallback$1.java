package com.google.android.systemui.columbus;

import android.content.Context;
import com.android.systemui.settings.UserTracker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ColumbusContentObserver.kt */
public final class ColumbusContentObserver$userTrackerCallback$1 implements UserTracker.Callback {
    final /* synthetic */ ColumbusContentObserver this$0;

    ColumbusContentObserver$userTrackerCallback$1(ColumbusContentObserver columbusContentObserver) {
        this.this$0 = columbusContentObserver;
    }

    @Override // com.android.systemui.settings.UserTracker.Callback
    public void onUserChanged(int i, Context context) {
        Intrinsics.checkNotNullParameter(context, "userContext");
        ColumbusContentObserver.access$updateContentObserver(this.this$0);
        ColumbusContentObserver.access$getCallback$p(this.this$0).invoke(ColumbusContentObserver.access$getSettingsUri$p(this.this$0));
    }
}
