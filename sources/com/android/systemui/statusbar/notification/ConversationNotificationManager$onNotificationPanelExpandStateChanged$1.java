package com.android.systemui.statusbar.notification;

import com.android.systemui.statusbar.notification.ConversationNotificationManager;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.Map;
import java.util.function.BiFunction;
import kotlin.jvm.internal.Intrinsics;

/* access modifiers changed from: package-private */
/* compiled from: ConversationNotifications.kt */
public final class ConversationNotificationManager$onNotificationPanelExpandStateChanged$1 implements BiFunction<String, ConversationNotificationManager.ConversationState, ConversationNotificationManager.ConversationState> {
    final /* synthetic */ Map<String, NotificationEntry> $expanded;

    ConversationNotificationManager$onNotificationPanelExpandStateChanged$1(Map<String, NotificationEntry> map) {
        this.$expanded = map;
    }

    public final ConversationNotificationManager.ConversationState apply(String str, ConversationNotificationManager.ConversationState conversationState) {
        Intrinsics.checkNotNullParameter(str, "key");
        Intrinsics.checkNotNullParameter(conversationState, "state");
        return this.$expanded.containsKey(str) ? ConversationNotificationManager.ConversationState.copy$default(conversationState, 0, null, 2, null) : conversationState;
    }
}
