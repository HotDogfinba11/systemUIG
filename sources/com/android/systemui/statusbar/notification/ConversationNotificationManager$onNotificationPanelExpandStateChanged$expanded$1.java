package com.android.systemui.statusbar.notification;

import com.android.systemui.statusbar.notification.ConversationNotificationManager;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import java.util.Map;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* access modifiers changed from: package-private */
/* compiled from: ConversationNotifications.kt */
public final class ConversationNotificationManager$onNotificationPanelExpandStateChanged$expanded$1 extends Lambda implements Function1<Map.Entry<? extends String, ? extends ConversationNotificationManager.ConversationState>, Pair<? extends String, ? extends NotificationEntry>> {
    final /* synthetic */ ConversationNotificationManager this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    ConversationNotificationManager$onNotificationPanelExpandStateChanged$expanded$1(ConversationNotificationManager conversationNotificationManager) {
        super(1);
        this.this$0 = conversationNotificationManager;
    }

    /* Return type fixed from 'java.lang.Object' to match base method */
    /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object] */
    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Pair<? extends String, ? extends NotificationEntry> invoke(Map.Entry<? extends String, ? extends ConversationNotificationManager.ConversationState> entry) {
        return invoke((Map.Entry<String, ConversationNotificationManager.ConversationState>) entry);
    }

    public final Pair<String, NotificationEntry> invoke(Map.Entry<String, ConversationNotificationManager.ConversationState> entry) {
        Intrinsics.checkNotNullParameter(entry, "$dstr$key$_u24__u24");
        String key = entry.getKey();
        NotificationEntry activeNotificationUnfiltered = this.this$0.notificationEntryManager.getActiveNotificationUnfiltered(key);
        if (activeNotificationUnfiltered == null) {
            return null;
        }
        ExpandableNotificationRow row = activeNotificationUnfiltered.getRow();
        if (Intrinsics.areEqual(row == null ? null : Boolean.valueOf(row.isExpanded()), Boolean.TRUE)) {
            return TuplesKt.to(key, activeNotificationUnfiltered);
        }
        return null;
    }
}
