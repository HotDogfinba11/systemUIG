package com.android.systemui.biometrics;

import android.graphics.PointF;
import android.util.Log;
import com.android.systemui.biometrics.UdfpsController;
import com.android.systemui.util.ViewController;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: AuthRippleController.kt */
public final class AuthRippleController$udfpsControllerCallback$1 implements UdfpsController.Callback {
    final /* synthetic */ AuthRippleController this$0;

    AuthRippleController$udfpsControllerCallback$1(AuthRippleController authRippleController) {
        this.this$0 = authRippleController;
    }

    @Override // com.android.systemui.biometrics.UdfpsController.Callback
    public void onFingerDown() {
        if (this.this$0.getFingerprintSensorLocation() == null) {
            Log.e("AuthRipple", "fingerprintSensorLocation=null onFingerDown. Skip showing dwell ripple");
            return;
        }
        PointF fingerprintSensorLocation = this.this$0.getFingerprintSensorLocation();
        Intrinsics.checkNotNull(fingerprintSensorLocation);
        ((AuthRippleView) ((ViewController) this.this$0).mView).setSensorLocation(fingerprintSensorLocation);
        this.this$0.showDwellRipple();
    }

    @Override // com.android.systemui.biometrics.UdfpsController.Callback
    public void onFingerUp() {
        ((AuthRippleView) ((ViewController) this.this$0).mView).retractRipple();
    }
}
