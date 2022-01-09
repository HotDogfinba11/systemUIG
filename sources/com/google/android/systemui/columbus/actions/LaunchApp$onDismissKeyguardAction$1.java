package com.google.android.systemui.columbus.actions;

import com.android.systemui.plugins.ActivityStarter;

/* access modifiers changed from: package-private */
/* compiled from: LaunchApp.kt */
public final class LaunchApp$onDismissKeyguardAction$1 implements ActivityStarter.OnDismissAction {
    final /* synthetic */ LaunchApp this$0;

    LaunchApp$onDismissKeyguardAction$1(LaunchApp launchApp) {
        this.this$0 = launchApp;
    }

    @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
    public final boolean onDismiss() {
        this.this$0.launchUnsecureAppInternal();
        return false;
    }
}
