package com.android.wm.shell.common.split;

import android.animation.ValueAnimator;

public final /* synthetic */ class SplitLayout$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ SplitLayout f$0;

    public /* synthetic */ SplitLayout$$ExternalSyntheticLambda0(SplitLayout splitLayout) {
        this.f$0 = splitLayout;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.f$0.lambda$flingDividePosition$0(valueAnimator);
    }
}
