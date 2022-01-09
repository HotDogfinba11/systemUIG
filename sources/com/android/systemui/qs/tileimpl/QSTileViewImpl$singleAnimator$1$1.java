package com.android.systemui.qs.tileimpl;

import android.animation.ValueAnimator;
import java.util.Objects;

/* access modifiers changed from: package-private */
/* compiled from: QSTileViewImpl.kt */
public final class QSTileViewImpl$singleAnimator$1$1 implements ValueAnimator.AnimatorUpdateListener {
    final /* synthetic */ QSTileViewImpl this$0;

    QSTileViewImpl$singleAnimator$1$1(QSTileViewImpl qSTileViewImpl) {
        this.this$0 = qSTileViewImpl;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        QSTileViewImpl qSTileViewImpl = this.this$0;
        Object animatedValue = valueAnimator.getAnimatedValue("background");
        Objects.requireNonNull(animatedValue, "null cannot be cast to non-null type kotlin.Int");
        int intValue = ((Integer) animatedValue).intValue();
        Object animatedValue2 = valueAnimator.getAnimatedValue("label");
        Objects.requireNonNull(animatedValue2, "null cannot be cast to non-null type kotlin.Int");
        int intValue2 = ((Integer) animatedValue2).intValue();
        Object animatedValue3 = valueAnimator.getAnimatedValue("secondaryLabel");
        Objects.requireNonNull(animatedValue3, "null cannot be cast to non-null type kotlin.Int");
        int intValue3 = ((Integer) animatedValue3).intValue();
        Object animatedValue4 = valueAnimator.getAnimatedValue("chevron");
        Objects.requireNonNull(animatedValue4, "null cannot be cast to non-null type kotlin.Int");
        qSTileViewImpl.setAllColors(intValue, intValue2, intValue3, ((Integer) animatedValue4).intValue());
    }
}
