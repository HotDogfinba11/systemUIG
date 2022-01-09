package com.google.android.systemui.statusbar.notification.voicereplies;

import android.app.Notification;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

/* access modifiers changed from: package-private */
/* compiled from: NotificationVoiceReplyManager.kt */
public final class NotificationVoiceReplyController$extractCandidate$1 extends Lambda implements Function0<Notification.Builder> {
    final /* synthetic */ NotificationEntry $entry;
    final /* synthetic */ NotificationVoiceReplyController this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    NotificationVoiceReplyController$extractCandidate$1(NotificationVoiceReplyController notificationVoiceReplyController, NotificationEntry notificationEntry) {
        super(0);
        this.this$0 = notificationVoiceReplyController;
        this.$entry = notificationEntry;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Notification.Builder invoke() {
        return Notification.Builder.recoverBuilder(this.this$0.context, this.$entry.getSbn().getNotification());
    }
}
