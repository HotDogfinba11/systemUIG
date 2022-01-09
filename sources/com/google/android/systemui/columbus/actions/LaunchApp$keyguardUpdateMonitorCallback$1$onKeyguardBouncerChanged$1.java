package com.google.android.systemui.columbus.actions;

import android.content.Context;
import android.content.res.ColorStateList;
import com.google.android.systemui.columbus.ColumbusResourceHelper;

/* compiled from: LaunchApp.kt */
final class LaunchApp$keyguardUpdateMonitorCallback$1$onKeyguardBouncerChanged$1 implements Runnable {
    final /* synthetic */ Context $context;
    final /* synthetic */ LaunchApp this$0;

    LaunchApp$keyguardUpdateMonitorCallback$1$onKeyguardBouncerChanged$1(LaunchApp launchApp, Context context) {
        this.this$0 = launchApp;
        this.$context = context;
    }

    public final void run() {
        this.this$0.statusBarKeyguardViewManager.showBouncerMessage(this.$context.getString(ColumbusResourceHelper.BOUNCER_MESSAGE_RESID), ColorStateList.valueOf(-1));
    }
}
