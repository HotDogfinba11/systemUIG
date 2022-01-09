package com.google.android.systemui.assist.uihints;

import com.google.android.systemui.assist.uihints.edgelights.EdgeLightsController;

public final /* synthetic */ class NgaUiController$$ExternalSyntheticLambda5 implements EdgeLightsController.ModeChangeThrottler {
    public final /* synthetic */ NgaUiController f$0;

    public /* synthetic */ NgaUiController$$ExternalSyntheticLambda5(NgaUiController ngaUiController) {
        this.f$0 = ngaUiController;
    }

    @Override // com.google.android.systemui.assist.uihints.edgelights.EdgeLightsController.ModeChangeThrottler
    public final void runWhenReady(String str, Runnable runnable) {
        this.f$0.lambda$new$2(str, runnable);
    }
}
