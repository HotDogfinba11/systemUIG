package com.android.systemui.media;

import android.animation.ValueAnimator;
import java.util.Objects;

/* access modifiers changed from: package-private */
/* compiled from: LightSourceDrawable.kt */
public final class LightSourceDrawable$illuminate$1$2$1 implements ValueAnimator.AnimatorUpdateListener {
    final /* synthetic */ LightSourceDrawable this$0;

    LightSourceDrawable$illuminate$1$2$1(LightSourceDrawable lightSourceDrawable) {
        this.this$0 = lightSourceDrawable;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        RippleData rippleData = this.this$0.rippleData;
        Object animatedValue = valueAnimator.getAnimatedValue();
        Objects.requireNonNull(animatedValue, "null cannot be cast to non-null type kotlin.Float");
        rippleData.setProgress(((Float) animatedValue).floatValue());
        this.this$0.invalidateSelf();
    }
}
