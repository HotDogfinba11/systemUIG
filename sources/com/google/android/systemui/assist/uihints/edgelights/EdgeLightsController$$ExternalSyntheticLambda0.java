package com.google.android.systemui.assist.uihints.edgelights;

import com.google.android.systemui.assist.uihints.edgelights.EdgeLightsView;

public final /* synthetic */ class EdgeLightsController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ EdgeLightsController f$0;
    public final /* synthetic */ EdgeLightsView.Mode f$1;

    public /* synthetic */ EdgeLightsController$$ExternalSyntheticLambda0(EdgeLightsController edgeLightsController, EdgeLightsView.Mode mode) {
        this.f$0 = edgeLightsController;
        this.f$1 = mode;
    }

    public final void run() {
        this.f$0.lambda$onEdgeLightsInfo$0(this.f$1);
    }
}
