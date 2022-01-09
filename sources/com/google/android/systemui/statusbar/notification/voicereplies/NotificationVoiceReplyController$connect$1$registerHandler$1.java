package com.google.android.systemui.statusbar.notification.voicereplies;

import com.android.systemui.statusbar.notification.people.Subscription;
import com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;

/* compiled from: NotificationVoiceReplyManager.kt */
public final class NotificationVoiceReplyController$connect$1$registerHandler$1 implements VoiceReplySubscription, Subscription {
    private final /* synthetic */ Subscription $$delegate_0;
    final /* synthetic */ NotificationVoiceReplyController.Connection $connection;
    final /* synthetic */ NotificationVoiceReplyHandler $handler;
    final /* synthetic */ Subscription $sub;
    final /* synthetic */ NotificationVoiceReplyController$connect$1 this$0;
    final /* synthetic */ NotificationVoiceReplyController this$1;

    @Override // com.android.systemui.statusbar.notification.people.Subscription
    public void unsubscribe() {
        this.$$delegate_0.unsubscribe();
    }

    NotificationVoiceReplyController$connect$1$registerHandler$1(Subscription subscription, NotificationVoiceReplyController$connect$1 notificationVoiceReplyController$connect$1, NotificationVoiceReplyController notificationVoiceReplyController, NotificationVoiceReplyController.Connection connection, NotificationVoiceReplyHandler notificationVoiceReplyHandler) {
        this.$sub = subscription;
        this.this$0 = notificationVoiceReplyController$connect$1;
        this.this$1 = notificationVoiceReplyController;
        this.$connection = connection;
        this.$handler = notificationVoiceReplyHandler;
        this.$$delegate_0 = subscription;
    }

    @Override // com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplySubscription
    public Object startVoiceReply(int i, String str, Function0<Unit> function0, Function2<? super Session, ? super Continuation<? super Unit>, ? extends Object> function2, Continuation<? super Unit> continuation) {
        this.this$0.ensureConnected();
        Object startVoiceReply = this.this$1.startVoiceReply(this.$connection, this.$handler, i, str, function0, function2, continuation);
        return startVoiceReply == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? startVoiceReply : Unit.INSTANCE;
    }
}
