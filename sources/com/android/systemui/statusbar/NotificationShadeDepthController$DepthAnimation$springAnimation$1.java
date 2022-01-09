package com.android.systemui.statusbar;

import androidx.dynamicanimation.animation.FloatPropertyCompat;
import com.android.systemui.statusbar.NotificationShadeDepthController;

/* compiled from: NotificationShadeDepthController.kt */
public final class NotificationShadeDepthController$DepthAnimation$springAnimation$1 extends FloatPropertyCompat<NotificationShadeDepthController.DepthAnimation> {
    final /* synthetic */ NotificationShadeDepthController.DepthAnimation this$0;
    final /* synthetic */ NotificationShadeDepthController this$1;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    NotificationShadeDepthController$DepthAnimation$springAnimation$1(NotificationShadeDepthController.DepthAnimation depthAnimation, NotificationShadeDepthController notificationShadeDepthController) {
        super("blurRadius");
        this.this$0 = depthAnimation;
        this.this$1 = notificationShadeDepthController;
    }

    public void setValue(NotificationShadeDepthController.DepthAnimation depthAnimation, float f) {
        this.this$0.setRadius(f);
        this.this$1.scheduleUpdate(this.this$0.view);
    }

    public float getValue(NotificationShadeDepthController.DepthAnimation depthAnimation) {
        return this.this$0.getRadius();
    }
}
