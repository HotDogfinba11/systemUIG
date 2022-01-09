package com.google.android.systemui.assist.uihints.edgelights;

import com.android.systemui.assist.ui.EdgeLight;

public final /* synthetic */ class EdgeLightsView$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ EdgeLightsView f$0;
    public final /* synthetic */ EdgeLight[] f$1;

    public /* synthetic */ EdgeLightsView$$ExternalSyntheticLambda0(EdgeLightsView edgeLightsView, EdgeLight[] edgeLightArr) {
        this.f$0 = edgeLightsView;
        this.f$1 = edgeLightArr;
    }

    public final void run() {
        this.f$0.lambda$setAssistLights$1(this.f$1);
    }
}
