package com.android.systemui.qs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: AutoAddTracker.kt */
public final class AutoAddTracker$restoreReceiver$1 extends BroadcastReceiver {
    final /* synthetic */ AutoAddTracker this$0;

    AutoAddTracker$restoreReceiver$1(AutoAddTracker autoAddTracker) {
        this.this$0 = autoAddTracker;
    }

    public void onReceive(Context context, Intent intent) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(intent, "intent");
        if (Intrinsics.areEqual(intent.getAction(), "android.os.action.SETTING_RESTORED")) {
            this.this$0.processRestoreIntent(intent);
        }
    }
}
