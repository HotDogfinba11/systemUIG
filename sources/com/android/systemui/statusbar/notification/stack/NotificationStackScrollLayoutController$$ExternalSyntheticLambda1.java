package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.statusbar.notification.DynamicPrivacyController;

public final /* synthetic */ class NotificationStackScrollLayoutController$$ExternalSyntheticLambda1 implements DynamicPrivacyController.Listener {
    public final /* synthetic */ NotificationStackScrollLayoutController f$0;

    public /* synthetic */ NotificationStackScrollLayoutController$$ExternalSyntheticLambda1(NotificationStackScrollLayoutController notificationStackScrollLayoutController) {
        this.f$0 = notificationStackScrollLayoutController;
    }

    @Override // com.android.systemui.statusbar.notification.DynamicPrivacyController.Listener
    public final void onDynamicPrivacyChanged() {
        this.f$0.lambda$new$1();
    }
}
