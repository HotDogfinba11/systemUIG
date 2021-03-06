package com.android.systemui.statusbar.notification;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.policy.OnHeadsUpChangedListener;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ConversationNotifications.kt */
public final class AnimatedImageNotificationManager$bind$1 implements OnHeadsUpChangedListener {
    final /* synthetic */ AnimatedImageNotificationManager this$0;

    AnimatedImageNotificationManager$bind$1(AnimatedImageNotificationManager animatedImageNotificationManager) {
        this.this$0 = animatedImageNotificationManager;
    }

    @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
    public void onHeadsUpStateChanged(NotificationEntry notificationEntry, boolean z) {
        Intrinsics.checkNotNullParameter(notificationEntry, "entry");
        ExpandableNotificationRow row = notificationEntry.getRow();
        if (row != null) {
            AnimatedImageNotificationManager animatedImageNotificationManager = this.this$0;
            animatedImageNotificationManager.updateAnimatedImageDrawables(row, z || (animatedImageNotificationManager.isStatusBarExpanded));
        }
    }
}
