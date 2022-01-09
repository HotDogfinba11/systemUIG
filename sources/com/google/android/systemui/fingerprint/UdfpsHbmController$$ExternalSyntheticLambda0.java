package com.google.android.systemui.fingerprint;

import com.google.android.systemui.fingerprint.UdfpsHbmRequest;

public final /* synthetic */ class UdfpsHbmController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ UdfpsHbmController f$0;
    public final /* synthetic */ UdfpsHbmRequest.Args f$1;

    public /* synthetic */ UdfpsHbmController$$ExternalSyntheticLambda0(UdfpsHbmController udfpsHbmController, UdfpsHbmRequest.Args args) {
        this.f$0 = udfpsHbmController;
        this.f$1 = args;
    }

    public final void run() {
        this.f$0.lambda$doEnableHbm$1(this.f$1);
    }
}
