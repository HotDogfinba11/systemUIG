package com.google.android.systemui.assist.uihints;

import android.animation.ValueAnimator;

public final /* synthetic */ class ScrimController$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ ScrimController f$0;

    public /* synthetic */ ScrimController$$ExternalSyntheticLambda0(ScrimController scrimController) {
        this.f$0 = scrimController;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.f$0.lambda$createRelativeAlphaAnimator$0(valueAnimator);
    }
}
