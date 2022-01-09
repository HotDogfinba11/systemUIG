package com.android.systemui.biometrics;

import android.animation.ValueAnimator;
import com.android.systemui.statusbar.LightRevealScrim;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: AuthRippleController.kt */
final class AuthRippleController$onKeyguardFadingAwayChanged$revealAnimator$1$1 implements ValueAnimator.AnimatorUpdateListener {
    final /* synthetic */ LightRevealScrim $lightRevealScrim;
    final /* synthetic */ AuthRippleController this$0;

    AuthRippleController$onKeyguardFadingAwayChanged$revealAnimator$1$1(LightRevealScrim lightRevealScrim, AuthRippleController authRippleController) {
        this.$lightRevealScrim = lightRevealScrim;
        this.this$0 = authRippleController;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        if (Intrinsics.areEqual(this.$lightRevealScrim.getRevealEffect(), this.this$0.circleReveal)) {
            LightRevealScrim lightRevealScrim = this.$lightRevealScrim;
            Object animatedValue = valueAnimator.getAnimatedValue();
            Objects.requireNonNull(animatedValue, "null cannot be cast to non-null type kotlin.Float");
            lightRevealScrim.setRevealAmount(((Float) animatedValue).floatValue());
        }
    }
}
