package com.google.android.systemui.assist.uihints;

import android.animation.ValueAnimator;
import android.view.animation.OvershootInterpolator;

public final /* synthetic */ class NgaUiController$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ NgaUiController f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ OvershootInterpolator f$2;

    public /* synthetic */ NgaUiController$$ExternalSyntheticLambda0(NgaUiController ngaUiController, int i, OvershootInterpolator overshootInterpolator) {
        this.f$0 = ngaUiController;
        this.f$1 = i;
        this.f$2 = overshootInterpolator;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.f$0.lambda$completeInvocation$6(this.f$1, this.f$2, valueAnimator);
    }
}
