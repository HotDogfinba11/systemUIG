package com.android.systemui.statusbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: LockscreenShadeTransitionController.kt */
public final class LockscreenShadeTransitionController$setDragDownAmountAnimated$2 extends AnimatorListenerAdapter {
    final /* synthetic */ Function0<Unit> $endlistener;

    LockscreenShadeTransitionController$setDragDownAmountAnimated$2(Function0<Unit> function0) {
        this.$endlistener = function0;
    }

    public void onAnimationEnd(Animator animator) {
        this.$endlistener.invoke();
    }
}
