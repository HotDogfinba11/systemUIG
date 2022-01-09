package com.google.android.systemui.assist.uihints.edgelights.mode;

import android.animation.ValueAnimator;

public final /* synthetic */ class FulfillBottom$$ExternalSyntheticLambda2 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ FulfillBottom f$0;
    public final /* synthetic */ float f$1;
    public final /* synthetic */ float f$2;
    public final /* synthetic */ float f$3;

    public /* synthetic */ FulfillBottom$$ExternalSyntheticLambda2(FulfillBottom fulfillBottom, float f, float f2, float f3) {
        this.f$0 = fulfillBottom;
        this.f$1 = f;
        this.f$2 = f2;
        this.f$3 = f3;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.f$0.lambda$animateCradle$0(this.f$1, this.f$2, this.f$3, valueAnimator);
    }
}
