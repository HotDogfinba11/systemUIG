package com.google.android.systemui.gamedashboard;

import com.google.android.systemui.gamedashboard.FpsController;

public final /* synthetic */ class FpsController$1$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ FpsController.AnonymousClass1 f$0;
    public final /* synthetic */ float f$1;

    public /* synthetic */ FpsController$1$$ExternalSyntheticLambda0(FpsController.AnonymousClass1 r1, float f) {
        this.f$0 = r1;
        this.f$1 = f;
    }

    public final void run() {
        this.f$0.lambda$onFpsReported$0(this.f$1);
    }
}
