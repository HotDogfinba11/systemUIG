package com.android.wm.shell.transition;

import android.animation.ValueAnimator;
import android.view.SurfaceControl;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public final /* synthetic */ class DefaultTransitionHandler$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ ValueAnimator f$0;
    public final /* synthetic */ SurfaceControl.Transaction f$1;
    public final /* synthetic */ SurfaceControl f$2;
    public final /* synthetic */ Animation f$3;
    public final /* synthetic */ Transformation f$4;
    public final /* synthetic */ float[] f$5;

    public /* synthetic */ DefaultTransitionHandler$$ExternalSyntheticLambda0(ValueAnimator valueAnimator, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, Animation animation, Transformation transformation, float[] fArr) {
        this.f$0 = valueAnimator;
        this.f$1 = transaction;
        this.f$2 = surfaceControl;
        this.f$3 = animation;
        this.f$4 = transformation;
        this.f$5 = fArr;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        DefaultTransitionHandler.$r8$lambda$LcEA68FEy6IXYeeukiPl3fAus5o(this.f$0, this.f$1, this.f$2, this.f$3, this.f$4, this.f$5, valueAnimator);
    }
}
