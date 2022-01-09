package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManager;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;

public final /* synthetic */ class NotificationStackScrollLayoutController$$ExternalSyntheticLambda3 implements GroupExpansionManager.OnGroupExpansionChangeListener {
    public final /* synthetic */ NotificationStackScrollLayoutController f$0;

    public /* synthetic */ NotificationStackScrollLayoutController$$ExternalSyntheticLambda3(NotificationStackScrollLayoutController notificationStackScrollLayoutController) {
        this.f$0 = notificationStackScrollLayoutController;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.GroupExpansionManager.OnGroupExpansionChangeListener
    public final void onGroupExpansionChange(ExpandableNotificationRow expandableNotificationRow, boolean z) {
        this.f$0.lambda$new$2(expandableNotificationRow, z);
    }
}
