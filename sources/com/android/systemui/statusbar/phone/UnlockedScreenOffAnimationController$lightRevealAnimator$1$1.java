package com.android.systemui.statusbar.phone;

import android.animation.ValueAnimator;
import com.android.systemui.statusbar.LightRevealScrim;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* access modifiers changed from: package-private */
/* compiled from: UnlockedScreenOffAnimationController.kt */
public final class UnlockedScreenOffAnimationController$lightRevealAnimator$1$1 implements ValueAnimator.AnimatorUpdateListener {
    final /* synthetic */ UnlockedScreenOffAnimationController this$0;

    UnlockedScreenOffAnimationController$lightRevealAnimator$1$1(UnlockedScreenOffAnimationController unlockedScreenOffAnimationController) {
        this.this$0 = unlockedScreenOffAnimationController;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        LightRevealScrim lightRevealScrim = this.this$0.lightRevealScrim;
        if (lightRevealScrim != null) {
            Object animatedValue = valueAnimator.getAnimatedValue();
            Objects.requireNonNull(animatedValue, "null cannot be cast to non-null type kotlin.Float");
            lightRevealScrim.setRevealAmount(((Float) animatedValue).floatValue());
            Object animatedValue2 = valueAnimator.getAnimatedValue();
            Objects.requireNonNull(animatedValue2, "null cannot be cast to non-null type kotlin.Float");
            this.this$0.sendUnlockedScreenOffProgressUpdate(1.0f - valueAnimator.getAnimatedFraction(), 1.0f - ((Float) animatedValue2).floatValue());
            return;
        }
        Intrinsics.throwUninitializedPropertyAccessException("lightRevealScrim");
        throw null;
    }
}
