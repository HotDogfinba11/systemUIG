package com.android.systemui.statusbar.charging;

/* compiled from: WiredChargingRippleController.kt */
final class WiredChargingRippleController$startRipple$1$onViewAttachedToWindow$1 implements Runnable {
    final /* synthetic */ WiredChargingRippleController this$0;

    WiredChargingRippleController$startRipple$1$onViewAttachedToWindow$1(WiredChargingRippleController wiredChargingRippleController) {
        this.this$0 = wiredChargingRippleController;
    }

    public final void run() {
        this.this$0.windowManager.removeView(this.this$0.getRippleView());
    }
}
