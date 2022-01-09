package com.android.wm.shell.bubbles;

import android.animation.ValueAnimator;

public final /* synthetic */ class BubbleStackView$$ExternalSyntheticLambda2 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ BubbleStackView f$0;
    public final /* synthetic */ float f$1;

    public /* synthetic */ BubbleStackView$$ExternalSyntheticLambda2(BubbleStackView bubbleStackView, float f) {
        this.f$0 = bubbleStackView;
        this.f$1 = f;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.f$0.lambda$new$6(this.f$1, valueAnimator);
    }
}
