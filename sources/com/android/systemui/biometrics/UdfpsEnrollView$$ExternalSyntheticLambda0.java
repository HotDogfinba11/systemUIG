package com.android.systemui.biometrics;

public final /* synthetic */ class UdfpsEnrollView$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ UdfpsEnrollProgressBarDrawable f$0;

    public /* synthetic */ UdfpsEnrollView$$ExternalSyntheticLambda0(UdfpsEnrollProgressBarDrawable udfpsEnrollProgressBarDrawable) {
        this.f$0 = udfpsEnrollProgressBarDrawable;
    }

    public final void run() {
        this.f$0.onLastStepAcquired();
    }
}
