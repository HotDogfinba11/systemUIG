package com.android.systemui.privacy;

import android.content.Context;
import android.content.pm.UserInfo;
import com.android.systemui.settings.UserTracker;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PrivacyItemController.kt */
public final class PrivacyItemController$userTrackerCallback$1 implements UserTracker.Callback {
    final /* synthetic */ PrivacyItemController this$0;

    PrivacyItemController$userTrackerCallback$1(PrivacyItemController privacyItemController) {
        this.this$0 = privacyItemController;
    }

    @Override // com.android.systemui.settings.UserTracker.Callback
    public void onUserChanged(int i, Context context) {
        Intrinsics.checkNotNullParameter(context, "userContext");
        this.this$0.update(true);
    }

    @Override // com.android.systemui.settings.UserTracker.Callback
    public void onProfilesChanged(List<? extends UserInfo> list) {
        Intrinsics.checkNotNullParameter(list, "profiles");
        this.this$0.update(true);
    }
}
