package com.google.android.systemui.statusbar.notification.voicereplies;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* access modifiers changed from: package-private */
/* compiled from: NotificationVoiceReplyManager.kt */
public final class NotificationVoiceReplyController$queryInitialState$1 extends Lambda implements Function1<NotificationEntry, VoiceReplyTarget> {
    final /* synthetic */ NotificationVoiceReplyController.Connection $this_queryInitialState;
    final /* synthetic */ NotificationVoiceReplyController this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    NotificationVoiceReplyController$queryInitialState$1(NotificationVoiceReplyController notificationVoiceReplyController, NotificationVoiceReplyController.Connection connection) {
        super(1);
        this.this$0 = notificationVoiceReplyController;
        this.$this_queryInitialState = connection;
    }

    public final VoiceReplyTarget invoke(NotificationEntry notificationEntry) {
        NotificationVoiceReplyController notificationVoiceReplyController = this.this$0;
        NotificationVoiceReplyController.Connection connection = this.$this_queryInitialState;
        Intrinsics.checkNotNullExpressionValue(notificationEntry, "entry");
        return NotificationVoiceReplyController.extractCandidate$default(notificationVoiceReplyController, connection, notificationEntry, 0, null, 6, null);
    }
}
