package com.android.systemui.util.animation;

import android.animation.ValueAnimator;

/* access modifiers changed from: package-private */
/* compiled from: TransitionLayoutController.kt */
public final class TransitionLayoutController$1$1 implements ValueAnimator.AnimatorUpdateListener {
    final /* synthetic */ TransitionLayoutController this$0;

    TransitionLayoutController$1$1(TransitionLayoutController transitionLayoutController) {
        this.this$0 = transitionLayoutController;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.this$0.updateStateFromAnimation();
    }
}
