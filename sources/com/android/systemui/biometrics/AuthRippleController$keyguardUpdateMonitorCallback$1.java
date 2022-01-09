package com.android.systemui.biometrics;

import android.hardware.biometrics.BiometricSourceType;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.util.ViewController;

/* compiled from: AuthRippleController.kt */
public final class AuthRippleController$keyguardUpdateMonitorCallback$1 extends KeyguardUpdateMonitorCallback {
    final /* synthetic */ AuthRippleController this$0;

    AuthRippleController$keyguardUpdateMonitorCallback$1(AuthRippleController authRippleController) {
        this.this$0 = authRippleController;
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
    public void onBiometricAuthenticated(int i, BiometricSourceType biometricSourceType, boolean z) {
        this.this$0.showRipple(biometricSourceType);
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
    public void onBiometricAuthFailed(BiometricSourceType biometricSourceType) {
        ((AuthRippleView) ((ViewController) this.this$0).mView).retractRipple();
    }
}
