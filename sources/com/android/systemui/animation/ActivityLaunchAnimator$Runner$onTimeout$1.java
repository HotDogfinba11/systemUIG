package com.android.systemui.animation;

import com.android.systemui.animation.ActivityLaunchAnimator;

/* access modifiers changed from: package-private */
/* compiled from: ActivityLaunchAnimator.kt */
public final class ActivityLaunchAnimator$Runner$onTimeout$1 implements Runnable {
    final /* synthetic */ ActivityLaunchAnimator.Runner this$0;

    ActivityLaunchAnimator$Runner$onTimeout$1(ActivityLaunchAnimator.Runner runner) {
        this.this$0 = runner;
    }

    public final void run() {
        this.this$0.onAnimationTimedOut();
    }
}
