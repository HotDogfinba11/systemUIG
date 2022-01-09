package com.android.systemui.statusbar.events;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

/* compiled from: SystemStatusAnimationScheduler.kt */
public final class SystemStatusAnimationScheduler$systemAnimatorAdapter$1 extends AnimatorListenerAdapter {
    final /* synthetic */ SystemStatusAnimationScheduler this$0;

    SystemStatusAnimationScheduler$systemAnimatorAdapter$1(SystemStatusAnimationScheduler systemStatusAnimationScheduler) {
        this.this$0 = systemStatusAnimationScheduler;
    }

    public void onAnimationEnd(Animator animator) {
        this.this$0.notifySystemFinish();
    }

    public void onAnimationStart(Animator animator) {
        this.this$0.notifySystemStart();
    }
}
