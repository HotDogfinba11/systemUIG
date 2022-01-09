package com.android.wm.shell.bubbles.animation;

import android.animation.ValueAnimator;

public final /* synthetic */ class PhysicsAnimationLayout$PhysicsPropertyAnimator$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ Runnable f$0;

    public /* synthetic */ PhysicsAnimationLayout$PhysicsPropertyAnimator$$ExternalSyntheticLambda0(Runnable runnable) {
        this.f$0 = runnable;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.f$0.run();
    }
}
