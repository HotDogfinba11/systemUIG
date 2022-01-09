package com.google.android.systemui.assist.uihints;

import android.animation.ValueAnimator;

public final /* synthetic */ class GlowController$$ExternalSyntheticLambda1 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ GlowController f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ GlowController$$ExternalSyntheticLambda1(GlowController glowController, int i, int i2) {
        this.f$0 = glowController;
        this.f$1 = i;
        this.f$2 = i2;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.f$0.lambda$animateGlowTranslationY$2(this.f$1, this.f$2, valueAnimator);
    }
}
