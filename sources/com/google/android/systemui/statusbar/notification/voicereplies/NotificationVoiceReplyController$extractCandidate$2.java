package com.google.android.systemui.statusbar.notification.voicereplies;

import android.app.Notification;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.List;
import kotlin.Lazy;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* access modifiers changed from: package-private */
/* compiled from: NotificationVoiceReplyManager.kt */
public final class NotificationVoiceReplyController$extractCandidate$2 extends Lambda implements Function1<Notification.Action, VoiceReplyTarget> {
    final /* synthetic */ Lazy<Notification.Builder> $builderLazy;
    final /* synthetic */ NotificationEntry $entry;
    final /* synthetic */ List<NotificationVoiceReplyHandler> $handlers;
    final /* synthetic */ long $postTime;
    final /* synthetic */ NotificationVoiceReplyController this$0;

    /* JADX DEBUG: Multi-variable search result rejected for r5v0, resolved type: kotlin.Lazy<? extends android.app.Notification$Builder> */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    NotificationVoiceReplyController$extractCandidate$2(NotificationVoiceReplyController notificationVoiceReplyController, NotificationEntry notificationEntry, long j, Lazy<? extends Notification.Builder> lazy, List<NotificationVoiceReplyHandler> list) {
        super(1);
        this.this$0 = notificationVoiceReplyController;
        this.$entry = notificationEntry;
        this.$postTime = j;
        this.$builderLazy = lazy;
        this.$handlers = list;
    }

    public final VoiceReplyTarget invoke(Notification.Action action) {
        NotificationVoiceReplyController notificationVoiceReplyController = this.this$0;
        NotificationEntry notificationEntry = this.$entry;
        Intrinsics.checkNotNullExpressionValue(action, "action");
        return notificationVoiceReplyController.tryCreateVoiceReplyTarget(notificationEntry, action, this.$postTime, this.$builderLazy, this.$handlers);
    }
}
