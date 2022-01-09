package com.android.systemui.statusbar;

import android.animation.ValueAnimator;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: NotificationShadeDepthController.kt */
final class NotificationShadeDepthController$keyguardStateCallback$1$onKeyguardFadingAwayChanged$1$1 implements ValueAnimator.AnimatorUpdateListener {
    final /* synthetic */ NotificationShadeDepthController this$0;

    NotificationShadeDepthController$keyguardStateCallback$1$onKeyguardFadingAwayChanged$1$1(NotificationShadeDepthController notificationShadeDepthController) {
        this.this$0 = notificationShadeDepthController;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        Intrinsics.checkNotNullParameter(valueAnimator, "animation");
        NotificationShadeDepthController notificationShadeDepthController = this.this$0;
        BlurUtils blurUtils = notificationShadeDepthController.blurUtils;
        Object animatedValue = valueAnimator.getAnimatedValue();
        Objects.requireNonNull(animatedValue, "null cannot be cast to non-null type kotlin.Float");
        notificationShadeDepthController.setWakeAndUnlockBlurRadius(blurUtils.blurRadiusOfRatio(((Float) animatedValue).floatValue()));
    }
}
