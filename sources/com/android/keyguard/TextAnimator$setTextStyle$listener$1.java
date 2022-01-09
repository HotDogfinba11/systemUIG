package com.android.keyguard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

/* compiled from: TextAnimator.kt */
public final class TextAnimator$setTextStyle$listener$1 extends AnimatorListenerAdapter {
    final /* synthetic */ Runnable $onAnimationEnd;
    final /* synthetic */ TextAnimator this$0;

    TextAnimator$setTextStyle$listener$1(Runnable runnable, TextAnimator textAnimator) {
        this.$onAnimationEnd = runnable;
        this.this$0 = textAnimator;
    }

    public void onAnimationEnd(Animator animator) {
        this.$onAnimationEnd.run();
        this.this$0.getAnimator$frameworks__base__packages__SystemUI__android_common__SystemUI_core().removeListener(this);
    }

    public void onAnimationCancel(Animator animator) {
        this.this$0.getAnimator$frameworks__base__packages__SystemUI__android_common__SystemUI_core().removeListener(this);
    }
}
