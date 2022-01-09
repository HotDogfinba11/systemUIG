package com.android.systemui.biometrics;

import com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController;

public final /* synthetic */ class UdfpsKeyguardViewController$$ExternalSyntheticLambda0 implements UnlockedScreenOffAnimationController.Callback {
    public final /* synthetic */ UdfpsKeyguardViewController f$0;

    public /* synthetic */ UdfpsKeyguardViewController$$ExternalSyntheticLambda0(UdfpsKeyguardViewController udfpsKeyguardViewController) {
        this.f$0 = udfpsKeyguardViewController;
    }

    @Override // com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController.Callback
    public final void onUnlockedScreenOffProgressUpdate(float f, float f2) {
        this.f$0.lambda$new$0(f, f2);
    }
}
