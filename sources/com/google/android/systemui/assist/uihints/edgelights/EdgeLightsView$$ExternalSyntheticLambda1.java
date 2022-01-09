package com.google.android.systemui.assist.uihints.edgelights;

import java.util.function.Consumer;

public final /* synthetic */ class EdgeLightsView$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ EdgeLightsView f$0;

    public /* synthetic */ EdgeLightsView$$ExternalSyntheticLambda1(EdgeLightsView edgeLightsView) {
        this.f$0 = edgeLightsView;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.f$0.lambda$setAssistLights$0((EdgeLightsListener) obj);
    }
}
