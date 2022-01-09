package com.android.systemui.statusbar;

import com.android.systemui.plugins.statusbar.StatusBarStateController;

/* compiled from: NotificationShadeDepthController.kt */
public final class NotificationShadeDepthController$statusBarStateCallback$1 implements StatusBarStateController.StateListener {
    final /* synthetic */ NotificationShadeDepthController this$0;

    NotificationShadeDepthController$statusBarStateCallback$1(NotificationShadeDepthController notificationShadeDepthController) {
        this.this$0 = notificationShadeDepthController;
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public void onStateChanged(int i) {
        NotificationShadeDepthController notificationShadeDepthController = this.this$0;
        notificationShadeDepthController.updateShadeAnimationBlur(notificationShadeDepthController.getShadeExpansion(), this.this$0.prevTracking, this.this$0.prevShadeVelocity, this.this$0.prevShadeDirection);
        NotificationShadeDepthController.scheduleUpdate$default(this.this$0, null, 1, null);
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public void onDozingChanged(boolean z) {
        if (z) {
            this.this$0.getShadeAnimation().finishIfRunning();
            this.this$0.getBrightnessMirrorSpring().finishIfRunning();
        }
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public void onDozeAmountChanged(float f, float f2) {
        NotificationShadeDepthController notificationShadeDepthController = this.this$0;
        notificationShadeDepthController.setWakeAndUnlockBlurRadius(notificationShadeDepthController.blurUtils.blurRadiusOfRatio(f2));
        NotificationShadeDepthController.scheduleUpdate$default(this.this$0, null, 1, null);
    }
}
