package com.google.android.systemui.fingerprint;

import com.google.android.systemui.fingerprint.UdfpsHbmRequest;

public final /* synthetic */ class UdfpsHbmController$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ UdfpsHbmController f$0;
    public final /* synthetic */ UdfpsHbmRequest.Args f$1;
    public final /* synthetic */ Runnable f$2;

    public /* synthetic */ UdfpsHbmController$$ExternalSyntheticLambda3(UdfpsHbmController udfpsHbmController, UdfpsHbmRequest.Args args, Runnable runnable) {
        this.f$0 = udfpsHbmController;
        this.f$1 = args;
        this.f$2 = runnable;
    }

    public final void run() {
        this.f$0.lambda$doDisableHbm$3(this.f$1, this.f$2);
    }
}
