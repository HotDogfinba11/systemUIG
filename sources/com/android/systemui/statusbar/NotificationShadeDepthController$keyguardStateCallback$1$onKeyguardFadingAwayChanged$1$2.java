package com.android.systemui.statusbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

/* compiled from: NotificationShadeDepthController.kt */
public final class NotificationShadeDepthController$keyguardStateCallback$1$onKeyguardFadingAwayChanged$1$2 extends AnimatorListenerAdapter {
    final /* synthetic */ NotificationShadeDepthController this$0;

    NotificationShadeDepthController$keyguardStateCallback$1$onKeyguardFadingAwayChanged$1$2(NotificationShadeDepthController notificationShadeDepthController) {
        this.this$0 = notificationShadeDepthController;
    }

    public void onAnimationEnd(Animator animator) {
        this.this$0.keyguardAnimator = null;
        NotificationShadeDepthController.scheduleUpdate$default(this.this$0, null, 1, null);
    }
}
