package com.android.systemui.biometrics;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

/* compiled from: AuthRippleView.kt */
public final class AuthRippleView$startUnlockedRipple$animatorSet$1$1 extends AnimatorListenerAdapter {
    final /* synthetic */ Runnable $onAnimationEnd;
    final /* synthetic */ AuthRippleView this$0;

    AuthRippleView$startUnlockedRipple$animatorSet$1$1(AuthRippleView authRippleView, Runnable runnable) {
        this.this$0 = authRippleView;
        this.$onAnimationEnd = runnable;
    }

    public void onAnimationStart(Animator animator) {
        this.this$0.unlockedRippleInProgress = true;
        this.this$0.rippleShader.setShouldFadeOutRipple(true);
        this.this$0.setVisibility(0);
    }

    public void onAnimationEnd(Animator animator) {
        Runnable runnable = this.$onAnimationEnd;
        if (runnable != null) {
            runnable.run();
        }
        this.this$0.unlockedRippleInProgress = false;
        this.this$0.setVisibility(8);
    }
}
