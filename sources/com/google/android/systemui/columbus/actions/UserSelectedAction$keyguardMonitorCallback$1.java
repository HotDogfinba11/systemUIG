package com.google.android.systemui.columbus.actions;

import com.android.systemui.statusbar.policy.KeyguardStateController;

/* compiled from: UserSelectedAction.kt */
public final class UserSelectedAction$keyguardMonitorCallback$1 implements KeyguardStateController.Callback {
    final /* synthetic */ UserSelectedAction this$0;

    UserSelectedAction$keyguardMonitorCallback$1(UserSelectedAction userSelectedAction) {
        this.this$0 = userSelectedAction;
    }

    @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
    public void onKeyguardShowingChanged() {
        this.this$0.updateAvailable();
    }
}
