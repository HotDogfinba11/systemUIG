package com.android.systemui.statusbar.notification.people;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* compiled from: PeopleNotificationIdentifier.kt */
public interface PeopleNotificationIdentifier {
    int compareTo(int i, int i2);

    int getPeopleNotificationType(NotificationEntry notificationEntry);
}
