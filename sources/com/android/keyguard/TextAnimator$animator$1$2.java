package com.android.keyguard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

/* compiled from: TextAnimator.kt */
public final class TextAnimator$animator$1$2 extends AnimatorListenerAdapter {
    final /* synthetic */ TextAnimator this$0;

    TextAnimator$animator$1$2(TextAnimator textAnimator) {
        this.this$0 = textAnimator;
    }

    public void onAnimationEnd(Animator animator) {
        this.this$0.getTextInterpolator$frameworks__base__packages__SystemUI__android_common__SystemUI_core().rebase();
    }

    public void onAnimationCancel(Animator animator) {
        this.this$0.getTextInterpolator$frameworks__base__packages__SystemUI__android_common__SystemUI_core().rebase();
    }
}
