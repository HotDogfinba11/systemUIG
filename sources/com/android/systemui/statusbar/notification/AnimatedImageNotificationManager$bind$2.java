package com.android.systemui.statusbar.notification;

import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ConversationNotifications.kt */
public final class AnimatedImageNotificationManager$bind$2 implements StatusBarStateController.StateListener {
    final /* synthetic */ AnimatedImageNotificationManager this$0;

    AnimatedImageNotificationManager$bind$2(AnimatedImageNotificationManager animatedImageNotificationManager) {
        this.this$0 = animatedImageNotificationManager;
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public void onExpandedChanged(boolean z) {
        this.this$0.isStatusBarExpanded = z;
        List<NotificationEntry> activeNotificationsForCurrentUser = this.this$0.notificationEntryManager.getActiveNotificationsForCurrentUser();
        Intrinsics.checkNotNullExpressionValue(activeNotificationsForCurrentUser, "notificationEntryManager.activeNotificationsForCurrentUser");
        AnimatedImageNotificationManager animatedImageNotificationManager = this.this$0;
        Iterator<T> it = activeNotificationsForCurrentUser.iterator();
        while (it.hasNext()) {
            ExpandableNotificationRow row = it.next().getRow();
            if (row != null) {
                animatedImageNotificationManager.updateAnimatedImageDrawables(row, z || row.isHeadsUp());
            }
        }
    }
}
