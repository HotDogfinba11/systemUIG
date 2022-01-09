package com.android.systemui.controls.management;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ControlsAnimations.kt */
public final class ControlsAnimations$exitAnimation$1$1$1 extends AnimatorListenerAdapter {
    final /* synthetic */ Runnable $it;

    ControlsAnimations$exitAnimation$1$1$1(Runnable runnable) {
        this.$it = runnable;
    }

    public void onAnimationEnd(Animator animator) {
        Intrinsics.checkNotNullParameter(animator, "animation");
        this.$it.run();
    }
}
