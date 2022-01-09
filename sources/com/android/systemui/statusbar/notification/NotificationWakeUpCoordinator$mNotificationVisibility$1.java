package com.android.systemui.statusbar.notification;

import android.util.FloatProperty;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: NotificationWakeUpCoordinator.kt */
public final class NotificationWakeUpCoordinator$mNotificationVisibility$1 extends FloatProperty<NotificationWakeUpCoordinator> {
    NotificationWakeUpCoordinator$mNotificationVisibility$1() {
        super("notificationVisibility");
    }

    public void setValue(NotificationWakeUpCoordinator notificationWakeUpCoordinator, float f) {
        Intrinsics.checkNotNullParameter(notificationWakeUpCoordinator, "coordinator");
        notificationWakeUpCoordinator.setVisibilityAmount(f);
    }

    public Float get(NotificationWakeUpCoordinator notificationWakeUpCoordinator) {
        Intrinsics.checkNotNullParameter(notificationWakeUpCoordinator, "coordinator");
        return Float.valueOf(notificationWakeUpCoordinator.mLinearVisibilityAmount);
    }
}
