package com.google.android.systemui.statusbar;

import com.google.android.systemui.statusbar.notification.voicereplies.Session;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* access modifiers changed from: package-private */
@DebugMetadata(c = "com.google.android.systemui.statusbar.NotificationVoiceReplyManagerService$binder$1$startVoiceReply$4", f = "NotificationVoiceReplyManagerService.kt", l = {263}, m = "invokeSuspend")
/* compiled from: NotificationVoiceReplyManagerService.kt */
public final class NotificationVoiceReplyManagerService$binder$1$startVoiceReply$4 extends SuspendLambda implements Function2<Session, Continuation<? super Unit>, Object> {
    final /* synthetic */ CallbacksHandler $handler;
    final /* synthetic */ int $token;
    int label;
    private /* synthetic */ Session p$;
    final /* synthetic */ NotificationVoiceReplyManagerService$binder$1 this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    NotificationVoiceReplyManagerService$binder$1$startVoiceReply$4(CallbacksHandler callbacksHandler, int i, NotificationVoiceReplyManagerService$binder$1 notificationVoiceReplyManagerService$binder$1, Continuation<? super NotificationVoiceReplyManagerService$binder$1$startVoiceReply$4> continuation) {
        super(2, continuation);
        this.$handler = callbacksHandler;
        this.$token = i;
        this.this$0 = notificationVoiceReplyManagerService$binder$1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        NotificationVoiceReplyManagerService$binder$1$startVoiceReply$4 notificationVoiceReplyManagerService$binder$1$startVoiceReply$4 = new NotificationVoiceReplyManagerService$binder$1$startVoiceReply$4(this.$handler, this.$token, this.this$0, continuation);
        notificationVoiceReplyManagerService$binder$1$startVoiceReply$4.p$ = (Session) obj;
        return notificationVoiceReplyManagerService$binder$1$startVoiceReply$4;
    }

    public final Object invoke(Session session, Continuation<? super Unit> continuation) {
        return ((NotificationVoiceReplyManagerService$binder$1$startVoiceReply$4) create(session, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object obj2 = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            this.$handler.onVoiceReplyStarted(this.$token);
            NotificationVoiceReplyManagerService$binder$1$startVoiceReply$4$invokeSuspend$$inlined$filter$1 notificationVoiceReplyManagerService$binder$1$startVoiceReply$4$invokeSuspend$$inlined$filter$1 = new NotificationVoiceReplyManagerService$binder$1$startVoiceReply$4$invokeSuspend$$inlined$filter$1(this.this$0.getOnVoiceAuthStateChangedFlow(), this.this$0, this.$token);
            NotificationVoiceReplyManagerService$binder$1$startVoiceReply$4$invokeSuspend$$inlined$collect$1 notificationVoiceReplyManagerService$binder$1$startVoiceReply$4$invokeSuspend$$inlined$collect$1 = new NotificationVoiceReplyManagerService$binder$1$startVoiceReply$4$invokeSuspend$$inlined$collect$1(this);
            this.label = 1;
            if (notificationVoiceReplyManagerService$binder$1$startVoiceReply$4$invokeSuspend$$inlined$filter$1.collect(notificationVoiceReplyManagerService$binder$1$startVoiceReply$4$invokeSuspend$$inlined$collect$1, this) == obj2) {
                return obj2;
            }
        } else if (i == 1) {
            ResultKt.throwOnFailure(obj);
        } else {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        return Unit.INSTANCE;
    }
}
