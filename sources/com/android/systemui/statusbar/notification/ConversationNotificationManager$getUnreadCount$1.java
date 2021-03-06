package com.android.systemui.statusbar.notification;

import android.app.Notification;
import com.android.systemui.statusbar.notification.ConversationNotificationManager;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.function.BiFunction;
import kotlin.jvm.internal.Intrinsics;

/* access modifiers changed from: package-private */
/* compiled from: ConversationNotifications.kt */
public final class ConversationNotificationManager$getUnreadCount$1 implements BiFunction<String, ConversationNotificationManager.ConversationState, ConversationNotificationManager.ConversationState> {
    final /* synthetic */ NotificationEntry $entry;
    final /* synthetic */ Notification.Builder $recoveredBuilder;
    final /* synthetic */ ConversationNotificationManager this$0;

    ConversationNotificationManager$getUnreadCount$1(NotificationEntry notificationEntry, ConversationNotificationManager conversationNotificationManager, Notification.Builder builder) {
        this.$entry = notificationEntry;
        this.this$0 = conversationNotificationManager;
        this.$recoveredBuilder = builder;
    }

    public final ConversationNotificationManager.ConversationState apply(String str, ConversationNotificationManager.ConversationState conversationState) {
        Intrinsics.checkNotNullParameter(str, "$noName_0");
        int i = 1;
        if (conversationState != null) {
            i = this.this$0.shouldIncrementUnread(conversationState, this.$recoveredBuilder) ? conversationState.getUnreadCount() + 1 : conversationState.getUnreadCount();
        }
        Notification notification = this.$entry.getSbn().getNotification();
        Intrinsics.checkNotNullExpressionValue(notification, "entry.sbn.notification");
        return new ConversationNotificationManager.ConversationState(i, notification);
    }
}
