package com.android.systemui.controls.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

/* compiled from: ControlViewHolder.kt */
public final class ControlViewHolder$startBackgroundAnimation$1$2 extends AnimatorListenerAdapter {
    final /* synthetic */ ControlViewHolder this$0;

    ControlViewHolder$startBackgroundAnimation$1$2(ControlViewHolder controlViewHolder) {
        this.this$0 = controlViewHolder;
    }

    public void onAnimationEnd(Animator animator) {
        this.this$0.stateAnimator = null;
    }
}
