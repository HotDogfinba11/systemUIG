package com.google.android.systemui.elmyra.gates;

import android.content.Context;
import com.android.systemui.Dependency;
import com.android.systemui.statusbar.policy.KeyguardStateController;

public class KeyguardVisibility extends Gate {
    private final KeyguardStateController.Callback mKeyguardMonitorCallback = new KeyguardStateController.Callback() {
        /* class com.google.android.systemui.elmyra.gates.KeyguardVisibility.AnonymousClass1 */

        @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
        public void onKeyguardShowingChanged() {
            KeyguardVisibility.this.notifyListener();
        }
    };
    private final KeyguardStateController mKeyguardStateController = ((KeyguardStateController) Dependency.get(KeyguardStateController.class));

    public KeyguardVisibility(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    @Override // com.google.android.systemui.elmyra.gates.Gate
    public void onActivate() {
        this.mKeyguardStateController.addCallback(this.mKeyguardMonitorCallback);
    }

    /* access modifiers changed from: protected */
    @Override // com.google.android.systemui.elmyra.gates.Gate
    public void onDeactivate() {
        this.mKeyguardStateController.removeCallback(this.mKeyguardMonitorCallback);
    }

    /* access modifiers changed from: protected */
    @Override // com.google.android.systemui.elmyra.gates.Gate
    public boolean isBlocked() {
        return isKeyguardShowing();
    }

    public boolean isKeyguardShowing() {
        return this.mKeyguardStateController.isShowing();
    }

    public boolean isKeyguardOccluded() {
        return this.mKeyguardStateController.isOccluded();
    }
}
