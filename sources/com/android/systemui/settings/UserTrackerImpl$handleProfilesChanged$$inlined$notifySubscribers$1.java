package com.android.systemui.settings;

import com.android.systemui.settings.UserTracker;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UserTrackerImpl.kt */
public final class UserTrackerImpl$handleProfilesChanged$$inlined$notifySubscribers$1 implements Runnable {
    final /* synthetic */ DataItem $it;
    final /* synthetic */ List $profiles$inlined;

    public UserTrackerImpl$handleProfilesChanged$$inlined$notifySubscribers$1(DataItem dataItem, List list) {
        this.$it = dataItem;
        this.$profiles$inlined = list;
    }

    public final void run() {
        UserTracker.Callback callback = this.$it.getCallback().get();
        if (callback != null) {
            Intrinsics.checkNotNullExpressionValue(this.$profiles$inlined, "profiles");
            callback.onProfilesChanged(this.$profiles$inlined);
        }
    }
}
