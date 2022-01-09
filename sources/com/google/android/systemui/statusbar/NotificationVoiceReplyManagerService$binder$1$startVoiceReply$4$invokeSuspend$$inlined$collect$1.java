package com.google.android.systemui.statusbar;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: Collect.kt */
public final class NotificationVoiceReplyManagerService$binder$1$startVoiceReply$4$invokeSuspend$$inlined$collect$1 implements FlowCollector<OnVoiceAuthStateChangedData> {
    final /* synthetic */ NotificationVoiceReplyManagerService$binder$1$startVoiceReply$4 this$0;

    public NotificationVoiceReplyManagerService$binder$1$startVoiceReply$4$invokeSuspend$$inlined$collect$1(NotificationVoiceReplyManagerService$binder$1$startVoiceReply$4 notificationVoiceReplyManagerService$binder$1$startVoiceReply$4) {
        this.this$0 = notificationVoiceReplyManagerService$binder$1$startVoiceReply$4;
    }

    /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object, kotlin.coroutines.Continuation] */
    @Override // kotlinx.coroutines.flow.FlowCollector
    public Object emit(OnVoiceAuthStateChangedData onVoiceAuthStateChangedData, Continuation continuation) {
        this.this$0.p$.setVoiceAuthState(onVoiceAuthStateChangedData.component3());
        return Unit.INSTANCE;
    }
}
