package com.google.android.systemui.statusbar.notification.voicereplies;

import com.android.systemui.statusbar.policy.RemoteInputView;
import com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.MutableSharedFlow;

/* access modifiers changed from: package-private */
@DebugMetadata(c = "com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$stateMachine$2$inSession$2", f = "NotificationVoiceReplyManager.kt", l = {485}, m = "invokeSuspend")
/* compiled from: NotificationVoiceReplyManager.kt */
public final class NotificationVoiceReplyController$stateMachine$2$inSession$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ Function2<Session, Continuation<? super Unit>, Object> $block;
    final /* synthetic */ String $initialContent;
    final /* synthetic */ MutableSharedFlow<Pair<String, RemoteInputView>> $remoteInputViewActivatedForVoiceReply;
    final /* synthetic */ VoiceReplyTarget $target;
    final /* synthetic */ NotificationVoiceReplyController.Connection $this_stateMachine;
    Object L$0;
    int label;
    private /* synthetic */ CoroutineScope p$;
    final /* synthetic */ NotificationVoiceReplyController this$0;

    /* JADX DEBUG: Multi-variable search result rejected for r6v0, resolved type: kotlin.jvm.functions.Function2<? super com.google.android.systemui.statusbar.notification.voicereplies.Session, ? super kotlin.coroutines.Continuation<? super kotlin.Unit>, ? extends java.lang.Object> */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    NotificationVoiceReplyController$stateMachine$2$inSession$2(NotificationVoiceReplyController notificationVoiceReplyController, VoiceReplyTarget voiceReplyTarget, NotificationVoiceReplyController.Connection connection, String str, MutableSharedFlow<Pair<String, RemoteInputView>> mutableSharedFlow, Function2<? super Session, ? super Continuation<? super Unit>, ? extends Object> function2, Continuation<? super NotificationVoiceReplyController$stateMachine$2$inSession$2> continuation) {
        super(2, continuation);
        this.this$0 = notificationVoiceReplyController;
        this.$target = voiceReplyTarget;
        this.$this_stateMachine = connection;
        this.$initialContent = str;
        this.$remoteInputViewActivatedForVoiceReply = mutableSharedFlow;
        this.$block = function2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        NotificationVoiceReplyController$stateMachine$2$inSession$2 notificationVoiceReplyController$stateMachine$2$inSession$2 = new NotificationVoiceReplyController$stateMachine$2$inSession$2(this.this$0, this.$target, this.$this_stateMachine, this.$initialContent, this.$remoteInputViewActivatedForVoiceReply, this.$block, continuation);
        notificationVoiceReplyController$stateMachine$2$inSession$2.p$ = (CoroutineScope) obj;
        return notificationVoiceReplyController$stateMachine$2$inSession$2;
    }

    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((NotificationVoiceReplyController$stateMachine$2$inSession$2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x007f  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r18) {
        /*
        // Method dump skipped, instructions count: 161
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$stateMachine$2$inSession$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
