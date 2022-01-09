package com.android.systemui.controls.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: ControlViewHolder.kt */
public final class ControlViewHolder$animateStatusChange$fadeOut$1$1 extends AnimatorListenerAdapter {
    final /* synthetic */ Function0<Unit> $statusRowUpdater;

    ControlViewHolder$animateStatusChange$fadeOut$1$1(Function0<Unit> function0) {
        this.$statusRowUpdater = function0;
    }

    public void onAnimationEnd(Animator animator) {
        this.$statusRowUpdater.invoke();
    }
}
