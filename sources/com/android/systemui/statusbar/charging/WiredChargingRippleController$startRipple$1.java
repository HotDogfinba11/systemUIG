package com.android.systemui.statusbar.charging;

import android.view.View;

/* compiled from: WiredChargingRippleController.kt */
public final class WiredChargingRippleController$startRipple$1 implements View.OnAttachStateChangeListener {
    final /* synthetic */ WiredChargingRippleController this$0;

    public void onViewDetachedFromWindow(View view) {
    }

    WiredChargingRippleController$startRipple$1(WiredChargingRippleController wiredChargingRippleController) {
        this.this$0 = wiredChargingRippleController;
    }

    public void onViewAttachedToWindow(View view) {
        this.this$0.layoutRipple();
        this.this$0.getRippleView().startRipple(new WiredChargingRippleController$startRipple$1$onViewAttachedToWindow$1(this.this$0));
        this.this$0.getRippleView().removeOnAttachStateChangeListener(this);
    }
}
