package com.android.systemui.statusbar.notification;

import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;
import com.android.systemui.statusbar.phone.HeadsUpManagerPhone;
import com.android.systemui.statusbar.phone.NotificationShadeWindowViewController;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: NotificationLaunchAnimatorController.kt */
public final class NotificationLaunchAnimatorControllerProvider {
    private final HeadsUpManagerPhone headsUpManager;
    private final NotificationListContainer notificationListContainer;
    private final NotificationShadeWindowViewController notificationShadeWindowViewController;

    public NotificationLaunchAnimatorControllerProvider(NotificationShadeWindowViewController notificationShadeWindowViewController2, NotificationListContainer notificationListContainer2, HeadsUpManagerPhone headsUpManagerPhone) {
        Intrinsics.checkNotNullParameter(notificationShadeWindowViewController2, "notificationShadeWindowViewController");
        Intrinsics.checkNotNullParameter(notificationListContainer2, "notificationListContainer");
        Intrinsics.checkNotNullParameter(headsUpManagerPhone, "headsUpManager");
        this.notificationShadeWindowViewController = notificationShadeWindowViewController2;
        this.notificationListContainer = notificationListContainer2;
        this.headsUpManager = headsUpManagerPhone;
    }

    public final NotificationLaunchAnimatorController getAnimatorController(ExpandableNotificationRow expandableNotificationRow) {
        Intrinsics.checkNotNullParameter(expandableNotificationRow, "notification");
        return new NotificationLaunchAnimatorController(this.notificationShadeWindowViewController, this.notificationListContainer, this.headsUpManager, expandableNotificationRow);
    }
}
