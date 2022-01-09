package com.google.android.systemui.columbus.actions;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DeskClockAction.kt */
public final class DeskClockAction$alertReceiver$1 extends BroadcastReceiver {
    final /* synthetic */ DeskClockAction this$0;

    DeskClockAction$alertReceiver$1(DeskClockAction deskClockAction) {
        this.this$0 = deskClockAction;
    }

    public void onReceive(Context context, Intent intent) {
        String str = null;
        if (Intrinsics.areEqual(intent == null ? null : intent.getAction(), this.this$0.getAlertAction())) {
            DeskClockAction.access$setAlertFiring$p(this.this$0, true);
        } else {
            if (intent != null) {
                str = intent.getAction();
            }
            if (Intrinsics.areEqual(str, this.this$0.getDoneAction())) {
                DeskClockAction.access$setAlertFiring$p(this.this$0, false);
            }
        }
        DeskClockAction.access$updateAvailable(this.this$0);
    }
}
