package com.google.android.systemui.statusbar;

import com.android.systemui.tuner.TunerService;

public final /* synthetic */ class KeyguardIndicationControllerGoogle$$ExternalSyntheticLambda1 implements TunerService.Tunable {
    public final /* synthetic */ KeyguardIndicationControllerGoogle f$0;

    public /* synthetic */ KeyguardIndicationControllerGoogle$$ExternalSyntheticLambda1(KeyguardIndicationControllerGoogle keyguardIndicationControllerGoogle) {
        this.f$0 = keyguardIndicationControllerGoogle;
    }

    @Override // com.android.systemui.tuner.TunerService.Tunable
    public final void onTuningChanged(String str, String str2) {
        this.f$0.lambda$init$0(str, str2);
    }
}
