package com.android.systemui.statusbar.notification;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ConversationNotifications.kt */
public final class AnimatedImageNotificationManager$bind$3 implements NotificationEntryListener {
    final /* synthetic */ AnimatedImageNotificationManager this$0;

    AnimatedImageNotificationManager$bind$3(AnimatedImageNotificationManager animatedImageNotificationManager) {
        this.this$0 = animatedImageNotificationManager;
    }

    @Override // com.android.systemui.statusbar.notification.NotificationEntryListener
    public void onEntryInflated(NotificationEntry notificationEntry) {
        Intrinsics.checkNotNullParameter(notificationEntry, "entry");
        ExpandableNotificationRow row = notificationEntry.getRow();
        if (row != null) {
            AnimatedImageNotificationManager animatedImageNotificationManager = this.this$0;
            animatedImageNotificationManager.updateAnimatedImageDrawables(row, (animatedImageNotificationManager.isStatusBarExpanded) || row.isHeadsUp());
        }
    }

    @Override // com.android.systemui.statusbar.notification.NotificationEntryListener
    public void onEntryReinflated(NotificationEntry notificationEntry) {
        Intrinsics.checkNotNullParameter(notificationEntry, "entry");
        onEntryInflated(notificationEntry);
    }
}
