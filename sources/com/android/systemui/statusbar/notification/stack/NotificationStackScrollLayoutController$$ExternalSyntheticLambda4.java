package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import java.util.List;

public final /* synthetic */ class NotificationStackScrollLayoutController$$ExternalSyntheticLambda4 implements NotificationStackScrollLayout.DismissAllAnimationListener {
    public final /* synthetic */ NotificationStackScrollLayoutController f$0;

    public /* synthetic */ NotificationStackScrollLayoutController$$ExternalSyntheticLambda4(NotificationStackScrollLayoutController notificationStackScrollLayoutController) {
        this.f$0 = notificationStackScrollLayoutController;
    }

    @Override // com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.DismissAllAnimationListener
    public final void onAnimationEnd(List list, int i) {
        this.f$0.onAnimationEnd(list, i);
    }
}
