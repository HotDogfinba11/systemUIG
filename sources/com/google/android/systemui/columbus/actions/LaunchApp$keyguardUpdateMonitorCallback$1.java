package com.google.android.systemui.columbus.actions;

import android.content.Context;
import com.android.keyguard.KeyguardUpdateMonitorCallback;

/* compiled from: LaunchApp.kt */
public final class LaunchApp$keyguardUpdateMonitorCallback$1 extends KeyguardUpdateMonitorCallback {
    final /* synthetic */ Context $context;
    final /* synthetic */ LaunchApp this$0;

    LaunchApp$keyguardUpdateMonitorCallback$1(LaunchApp launchApp, Context context) {
        this.this$0 = launchApp;
        this.$context = context;
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
    public void onKeyguardBouncerChanged(boolean z) {
        if (z) {
            this.this$0.keyguardUpdateMonitor.removeCallback(this);
            this.this$0.mainHandler.post(new LaunchApp$keyguardUpdateMonitorCallback$1$onKeyguardBouncerChanged$1(this.this$0, this.$context));
        }
    }
}
