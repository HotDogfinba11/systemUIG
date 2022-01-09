package com.android.systemui.controls.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ControlsActivity.kt */
public final class ControlsActivity$initBroadcastReceiver$1 extends BroadcastReceiver {
    final /* synthetic */ ControlsActivity this$0;

    ControlsActivity$initBroadcastReceiver$1(ControlsActivity controlsActivity) {
        this.this$0 = controlsActivity;
    }

    public void onReceive(Context context, Intent intent) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(intent, "intent");
        if ("android.intent.action.SCREEN_OFF".equals(intent.getAction())) {
            this.this$0.finish();
        }
    }
}
