package com.android.wm.shell.legacysplitscreen;

import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.view.SurfaceControl;

public final /* synthetic */ class LegacySplitScreenTransitions$$ExternalSyntheticLambda1 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ SurfaceControl.Transaction f$0;
    public final /* synthetic */ SurfaceControl f$1;
    public final /* synthetic */ Rect f$2;
    public final /* synthetic */ Rect f$3;

    public /* synthetic */ LegacySplitScreenTransitions$$ExternalSyntheticLambda1(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, Rect rect, Rect rect2) {
        this.f$0 = transaction;
        this.f$1 = surfaceControl;
        this.f$2 = rect;
        this.f$3 = rect2;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        LegacySplitScreenTransitions.m564$r8$lambda$OAyspgWex5px6D9HZkdGMwkP64(this.f$0, this.f$1, this.f$2, this.f$3, valueAnimator);
    }
}
