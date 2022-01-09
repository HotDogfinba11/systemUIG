package com.google.android.systemui.assist.uihints;

import android.animation.ValueAnimator;

public final /* synthetic */ class GlowController$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ GlowController f$0;

    public /* synthetic */ GlowController$$ExternalSyntheticLambda0(GlowController glowController) {
        this.f$0 = glowController;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.f$0.lambda$animateGlowTranslationY$1(valueAnimator);
    }
}
