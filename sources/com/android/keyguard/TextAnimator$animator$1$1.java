package com.android.keyguard;

import android.animation.ValueAnimator;
import java.util.Objects;

/* access modifiers changed from: package-private */
/* compiled from: TextAnimator.kt */
public final class TextAnimator$animator$1$1 implements ValueAnimator.AnimatorUpdateListener {
    final /* synthetic */ TextAnimator this$0;

    TextAnimator$animator$1$1(TextAnimator textAnimator) {
        this.this$0 = textAnimator;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        TextInterpolator textInterpolator$frameworks__base__packages__SystemUI__android_common__SystemUI_core = this.this$0.getTextInterpolator$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
        Object animatedValue = valueAnimator.getAnimatedValue();
        Objects.requireNonNull(animatedValue, "null cannot be cast to non-null type kotlin.Float");
        textInterpolator$frameworks__base__packages__SystemUI__android_common__SystemUI_core.setProgress(((Float) animatedValue).floatValue());
        this.this$0.invalidateCallback.invoke();
    }
}
