package com.android.systemui.biometrics;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

/* compiled from: AuthRippleView.kt */
public final class AuthRippleView$startDwellRipple$1$1 extends AnimatorListenerAdapter {
    final /* synthetic */ AuthRippleView this$0;

    AuthRippleView$startDwellRipple$1$1(AuthRippleView authRippleView) {
        this.this$0 = authRippleView;
    }

    public void onAnimationStart(Animator animator) {
        Animator animator2 = this.this$0.retractAnimator;
        if (animator2 != null) {
            animator2.cancel();
        }
        this.this$0.rippleShader.setShouldFadeOutRipple(false);
        this.this$0.setVisibility(0);
    }

    public void onAnimationEnd(Animator animator) {
        this.this$0.setVisibility(8);
        this.this$0.resetRippleAlpha();
    }
}
