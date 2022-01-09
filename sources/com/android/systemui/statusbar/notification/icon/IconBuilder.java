package com.android.systemui.statusbar.notification.icon;

import android.app.Notification;
import android.content.Context;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: IconBuilder.kt */
public final class IconBuilder {
    private final Context context;

    public IconBuilder(Context context2) {
        Intrinsics.checkNotNullParameter(context2, "context");
        this.context = context2;
    }

    public final StatusBarIconView createIconView(NotificationEntry notificationEntry) {
        Intrinsics.checkNotNullParameter(notificationEntry, "entry");
        Context context2 = this.context;
        return new StatusBarIconView(context2, ((Object) notificationEntry.getSbn().getPackageName()) + "/0x" + ((Object) Integer.toHexString(notificationEntry.getSbn().getId())), notificationEntry.getSbn());
    }

    public final CharSequence getIconContentDescription(Notification notification) {
        Intrinsics.checkNotNullParameter(notification, "n");
        String contentDescForNotification = StatusBarIconView.contentDescForNotification(this.context, notification);
        Intrinsics.checkNotNullExpressionValue(contentDescForNotification, "contentDescForNotification(context, n)");
        return contentDescForNotification;
    }
}
