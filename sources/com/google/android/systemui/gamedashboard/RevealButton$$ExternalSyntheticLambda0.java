package com.google.android.systemui.gamedashboard;

import android.animation.ValueAnimator;
import android.graphics.Rect;

public final /* synthetic */ class RevealButton$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ RevealButton f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ Rect f$2;
    public final /* synthetic */ int f$3;

    public /* synthetic */ RevealButton$$ExternalSyntheticLambda0(RevealButton revealButton, int i, Rect rect, int i2) {
        this.f$0 = revealButton;
        this.f$1 = i;
        this.f$2 = rect;
        this.f$3 = i2;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        RevealButton.m679$r8$lambda$PLizT1tv1L203jH4W9Zh1hKQJY(this.f$0, this.f$1, this.f$2, this.f$3, valueAnimator);
    }
}
