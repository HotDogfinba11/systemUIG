package com.android.systemui.biometrics;

import android.animation.ValueAnimator;
import com.android.systemui.statusbar.charging.RippleShader;
import java.util.Objects;

/* access modifiers changed from: package-private */
/* compiled from: AuthRippleView.kt */
public final class AuthRippleView$startUnlockedRipple$rippleAnimator$1$1 implements ValueAnimator.AnimatorUpdateListener {
    final /* synthetic */ AuthRippleView this$0;

    AuthRippleView$startUnlockedRipple$rippleAnimator$1$1(AuthRippleView authRippleView) {
        this.this$0 = authRippleView;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        long currentPlayTime = valueAnimator.getCurrentPlayTime();
        RippleShader rippleShader = this.this$0.rippleShader;
        Object animatedValue = valueAnimator.getAnimatedValue();
        Objects.requireNonNull(animatedValue, "null cannot be cast to non-null type kotlin.Float");
        rippleShader.setProgress(((Float) animatedValue).floatValue());
        this.this$0.rippleShader.setTime((float) currentPlayTime);
        this.this$0.invalidate();
    }
}
