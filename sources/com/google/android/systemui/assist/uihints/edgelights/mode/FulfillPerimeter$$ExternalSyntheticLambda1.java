package com.google.android.systemui.assist.uihints.edgelights.mode;

import android.animation.ValueAnimator;
import com.android.systemui.assist.ui.EdgeLight;
import com.android.systemui.assist.ui.PerimeterPathGuide;
import com.google.android.systemui.assist.uihints.edgelights.EdgeLightsView;

public final /* synthetic */ class FulfillPerimeter$$ExternalSyntheticLambda1 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ FulfillPerimeter f$0;
    public final /* synthetic */ EdgeLight f$1;
    public final /* synthetic */ float f$2;
    public final /* synthetic */ PerimeterPathGuide f$3;
    public final /* synthetic */ EdgeLightsView f$4;

    public /* synthetic */ FulfillPerimeter$$ExternalSyntheticLambda1(FulfillPerimeter fulfillPerimeter, EdgeLight edgeLight, float f, PerimeterPathGuide perimeterPathGuide, EdgeLightsView edgeLightsView) {
        this.f$0 = fulfillPerimeter;
        this.f$1 = edgeLight;
        this.f$2 = f;
        this.f$3 = perimeterPathGuide;
        this.f$4 = edgeLightsView;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.f$0.lambda$start$1(this.f$1, this.f$2, this.f$3, this.f$4, valueAnimator);
    }
}
