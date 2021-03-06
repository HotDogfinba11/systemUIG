package com.android.systemui.animation;

import android.animation.ValueAnimator;
import com.android.systemui.animation.ActivityLaunchAnimator;

/* compiled from: ActivityLaunchAnimator.kt */
final class ActivityLaunchAnimator$Runner$onAnimationCancelled$1 implements Runnable {
    final /* synthetic */ ActivityLaunchAnimator.Runner this$0;

    ActivityLaunchAnimator$Runner$onAnimationCancelled$1(ActivityLaunchAnimator.Runner runner) {
        this.this$0 = runner;
    }

    public final void run() {
        ValueAnimator valueAnimator = this.this$0.animator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        this.this$0.controller.onLaunchAnimationCancelled();
    }
}
