package com.google.android.systemui.smartspace;

import com.android.systemui.statusbar.policy.ZenModeController;

/* compiled from: KeyguardZenAlarmViewController.kt */
public final class KeyguardZenAlarmViewController$zenModeCallback$1 implements ZenModeController.Callback {
    final /* synthetic */ KeyguardZenAlarmViewController this$0;

    KeyguardZenAlarmViewController$zenModeCallback$1(KeyguardZenAlarmViewController keyguardZenAlarmViewController) {
        this.this$0 = keyguardZenAlarmViewController;
    }

    @Override // com.android.systemui.statusbar.policy.ZenModeController.Callback
    public void onZenChanged(int i) {
        this.this$0.updateDnd();
    }
}
