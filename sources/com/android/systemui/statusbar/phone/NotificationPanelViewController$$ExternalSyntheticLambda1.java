package com.android.systemui.statusbar.phone;

import android.animation.ValueAnimator;

public final /* synthetic */ class NotificationPanelViewController$$ExternalSyntheticLambda1 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ NotificationPanelViewController f$0;

    public /* synthetic */ NotificationPanelViewController$$ExternalSyntheticLambda1(NotificationPanelViewController notificationPanelViewController) {
        this.f$0 = notificationPanelViewController;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.f$0.lambda$flingSettings$9(valueAnimator);
    }
}
