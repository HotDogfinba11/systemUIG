package com.google.android.systemui.columbus.feedback;

import android.animation.ValueAnimator;
import java.util.Objects;

/* compiled from: AssistInvocationEffect.kt */
final class AssistInvocationEffect$animatorUpdateListener$1 implements ValueAnimator.AnimatorUpdateListener {
    final /* synthetic */ AssistInvocationEffect this$0;

    AssistInvocationEffect$animatorUpdateListener$1(AssistInvocationEffect assistInvocationEffect) {
        this.this$0 = assistInvocationEffect;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        if (valueAnimator != null) {
            AssistInvocationEffect assistInvocationEffect = this.this$0;
            Object animatedValue = valueAnimator.getAnimatedValue();
            Objects.requireNonNull(animatedValue, "null cannot be cast to non-null type kotlin.Float");
            assistInvocationEffect.progress = ((Float) animatedValue).floatValue();
            assistInvocationEffect.updateAssistManager();
        }
    }
}
