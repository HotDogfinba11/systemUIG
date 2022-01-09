package com.android.systemui.biometrics;

import com.android.systemui.biometrics.AuthController;

/* access modifiers changed from: package-private */
/* compiled from: AuthRippleController.kt */
public final class AuthRippleController$authControllerCallback$1 implements AuthController.Callback {
    final /* synthetic */ AuthRippleController this$0;

    AuthRippleController$authControllerCallback$1(AuthRippleController authRippleController) {
        this.this$0 = authRippleController;
    }

    @Override // com.android.systemui.biometrics.AuthController.Callback
    public final void onAllAuthenticatorsRegistered() {
        this.this$0.updateSensorLocation();
        this.this$0.updateUdfpsDependentParams();
    }
}
