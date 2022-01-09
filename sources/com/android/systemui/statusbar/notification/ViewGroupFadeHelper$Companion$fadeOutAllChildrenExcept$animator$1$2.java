package com.android.systemui.statusbar.notification;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

/* compiled from: ViewGroupFadeHelper.kt */
public final class ViewGroupFadeHelper$Companion$fadeOutAllChildrenExcept$animator$1$2 extends AnimatorListenerAdapter {
    final /* synthetic */ Runnable $endRunnable;

    ViewGroupFadeHelper$Companion$fadeOutAllChildrenExcept$animator$1$2(Runnable runnable) {
        this.$endRunnable = runnable;
    }

    public void onAnimationEnd(Animator animator) {
        Runnable runnable = this.$endRunnable;
        if (runnable != null) {
            runnable.run();
        }
    }
}
