package com.android.wm.shell.bubbles;

import androidx.dynamicanimation.animation.DynamicAnimation;

public final /* synthetic */ class BubbleStackView$$ExternalSyntheticLambda11 implements DynamicAnimation.OnAnimationEndListener {
    public final /* synthetic */ BubbleStackView f$0;

    public /* synthetic */ BubbleStackView$$ExternalSyntheticLambda11(BubbleStackView bubbleStackView) {
        this.f$0 = bubbleStackView;
    }

    @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener
    public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z, float f, float f2) {
        this.f$0.lambda$new$1(dynamicAnimation, z, f, f2);
    }
}
