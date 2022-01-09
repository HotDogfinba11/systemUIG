package com.android.systemui.controls.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

/* compiled from: ToggleRangeBehavior.kt */
public final class ToggleRangeBehavior$updateRange$1$2 extends AnimatorListenerAdapter {
    final /* synthetic */ ToggleRangeBehavior this$0;

    ToggleRangeBehavior$updateRange$1$2(ToggleRangeBehavior toggleRangeBehavior) {
        this.this$0 = toggleRangeBehavior;
    }

    public void onAnimationEnd(Animator animator) {
        this.this$0.rangeAnimator = null;
    }
}
