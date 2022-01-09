package com.android.systemui.statusbar.lockscreen;

import android.content.Context;
import com.android.systemui.settings.UserTracker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: LockscreenSmartspaceController.kt */
public final class LockscreenSmartspaceController$userTrackerCallback$1 implements UserTracker.Callback {
    final /* synthetic */ LockscreenSmartspaceController this$0;

    LockscreenSmartspaceController$userTrackerCallback$1(LockscreenSmartspaceController lockscreenSmartspaceController) {
        this.this$0 = lockscreenSmartspaceController;
    }

    @Override // com.android.systemui.settings.UserTracker.Callback
    public void onUserChanged(int i, Context context) {
        Intrinsics.checkNotNullParameter(context, "userContext");
        this.this$0.execution.assertIsMainThread();
        this.this$0.reloadSmartspace();
    }
}
