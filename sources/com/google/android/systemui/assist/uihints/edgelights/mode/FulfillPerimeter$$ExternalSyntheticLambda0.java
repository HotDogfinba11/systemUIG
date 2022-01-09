package com.google.android.systemui.assist.uihints.edgelights.mode;

import android.animation.ValueAnimator;
import com.android.systemui.assist.ui.EdgeLight;
import com.google.android.systemui.assist.uihints.edgelights.EdgeLightsView;

public final /* synthetic */ class FulfillPerimeter$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ FulfillPerimeter f$0;
    public final /* synthetic */ EdgeLight f$1;
    public final /* synthetic */ float f$2;
    public final /* synthetic */ float f$3;
    public final /* synthetic */ float f$4;
    public final /* synthetic */ float f$5;
    public final /* synthetic */ EdgeLightsView f$6;

    public /* synthetic */ FulfillPerimeter$$ExternalSyntheticLambda0(FulfillPerimeter fulfillPerimeter, EdgeLight edgeLight, float f, float f2, float f3, float f4, EdgeLightsView edgeLightsView) {
        this.f$0 = fulfillPerimeter;
        this.f$1 = edgeLight;
        this.f$2 = f;
        this.f$3 = f2;
        this.f$4 = f3;
        this.f$5 = f4;
        this.f$6 = edgeLightsView;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.f$0.lambda$start$0(this.f$1, this.f$2, this.f$3, this.f$4, this.f$5, this.f$6, valueAnimator);
    }
}
