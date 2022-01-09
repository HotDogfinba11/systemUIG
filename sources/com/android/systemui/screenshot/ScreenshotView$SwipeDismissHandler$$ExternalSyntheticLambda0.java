package com.android.systemui.screenshot;

import android.animation.ValueAnimator;
import com.android.systemui.screenshot.ScreenshotView;

public final /* synthetic */ class ScreenshotView$SwipeDismissHandler$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ ScreenshotView.SwipeDismissHandler f$0;
    public final /* synthetic */ float f$1;
    public final /* synthetic */ float f$2;

    public /* synthetic */ ScreenshotView$SwipeDismissHandler$$ExternalSyntheticLambda0(ScreenshotView.SwipeDismissHandler swipeDismissHandler, float f, float f2) {
        this.f$0 = swipeDismissHandler;
        this.f$1 = f;
        this.f$2 = f2;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        ScreenshotView.SwipeDismissHandler.$r8$lambda$ESzr2GccPDv_7bsDcr3yQ95NBI0(this.f$0, this.f$1, this.f$2, valueAnimator);
    }
}
