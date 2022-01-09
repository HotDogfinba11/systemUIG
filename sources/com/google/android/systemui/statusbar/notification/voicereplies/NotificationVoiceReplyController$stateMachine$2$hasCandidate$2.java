package com.google.android.systemui.statusbar.notification.voicereplies;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;

/* access modifiers changed from: package-private */
@DebugMetadata(c = "com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$stateMachine$2$hasCandidate$2", f = "NotificationVoiceReplyManager.kt", l = {440}, m = "invokeSuspend")
/* compiled from: NotificationVoiceReplyManager.kt */
public final class NotificationVoiceReplyController$stateMachine$2$hasCandidate$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ VoiceReplyTarget $candidate;
    final /* synthetic */ Flow<NotificationEntry> $reinflations;
    final /* synthetic */ NotificationVoiceReplyController.Connection $this_stateMachine;
    Object L$0;
    int label;
    private /* synthetic */ CoroutineScope p$;
    final /* synthetic */ NotificationVoiceReplyController this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    NotificationVoiceReplyController$stateMachine$2$hasCandidate$2(NotificationVoiceReplyController.Connection connection, VoiceReplyTarget voiceReplyTarget, NotificationVoiceReplyController notificationVoiceReplyController, Flow<NotificationEntry> flow, Continuation<? super NotificationVoiceReplyController$stateMachine$2$hasCandidate$2> continuation) {
        super(2, continuation);
        this.$this_stateMachine = connection;
        this.$candidate = voiceReplyTarget;
        this.this$0 = notificationVoiceReplyController;
        this.$reinflations = flow;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        NotificationVoiceReplyController$stateMachine$2$hasCandidate$2 notificationVoiceReplyController$stateMachine$2$hasCandidate$2 = new NotificationVoiceReplyController$stateMachine$2$hasCandidate$2(this.$this_stateMachine, this.$candidate, this.this$0, this.$reinflations, continuation);
        notificationVoiceReplyController$stateMachine$2$hasCandidate$2.p$ = (CoroutineScope) obj;
        return notificationVoiceReplyController$stateMachine$2$hasCandidate$2;
    }

    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((NotificationVoiceReplyController$stateMachine$2$hasCandidate$2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x007e  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r18) {
        /*
        // Method dump skipped, instructions count: 207
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$stateMachine$2$hasCandidate$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
