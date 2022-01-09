package com.android.systemui.statusbar.charging;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

/* compiled from: ChargingRippleView.kt */
public final class ChargingRippleView$startRipple$2 extends AnimatorListenerAdapter {
    final /* synthetic */ Runnable $onAnimationEnd;
    final /* synthetic */ ChargingRippleView this$0;

    ChargingRippleView$startRipple$2(ChargingRippleView chargingRippleView, Runnable runnable) {
        this.this$0 = chargingRippleView;
        this.$onAnimationEnd = runnable;
    }

    public void onAnimationEnd(Animator animator) {
        this.this$0.setRippleInProgress(false);
        this.this$0.setVisibility(8);
        Runnable runnable = this.$onAnimationEnd;
        if (runnable != null) {
            runnable.run();
        }
    }
}
