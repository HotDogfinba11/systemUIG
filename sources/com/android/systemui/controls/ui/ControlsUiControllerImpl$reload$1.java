package com.android.systemui.controls.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ControlsUiControllerImpl.kt */
public final class ControlsUiControllerImpl$reload$1 extends AnimatorListenerAdapter {
    final /* synthetic */ ViewGroup $parent;
    final /* synthetic */ ControlsUiControllerImpl this$0;

    ControlsUiControllerImpl$reload$1(ControlsUiControllerImpl controlsUiControllerImpl, ViewGroup viewGroup) {
        this.this$0 = controlsUiControllerImpl;
        this.$parent = viewGroup;
    }

    public void onAnimationEnd(Animator animator) {
        Intrinsics.checkNotNullParameter(animator, "animation");
        this.this$0.controlViewsById.clear();
        this.this$0.controlsById.clear();
        ControlsUiControllerImpl controlsUiControllerImpl = this.this$0;
        ViewGroup viewGroup = this.$parent;
        Runnable runnable = controlsUiControllerImpl.onDismiss;
        if (runnable != null) {
            Context context = this.this$0.activityContext;
            if (context != null) {
                controlsUiControllerImpl.show(viewGroup, runnable, context);
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.$parent, "alpha", 0.0f, 1.0f);
                ofFloat.setInterpolator(new DecelerateInterpolator(1.0f));
                ofFloat.setDuration(200L);
                ofFloat.start();
                return;
            }
            Intrinsics.throwUninitializedPropertyAccessException("activityContext");
            throw null;
        }
        Intrinsics.throwUninitializedPropertyAccessException("onDismiss");
        throw null;
    }
}
