package com.google.android.systemui.statusbar;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* access modifiers changed from: package-private */
@DebugMetadata(c = "com.google.android.systemui.statusbar.NotificationVoiceReplyManagerService", f = "NotificationVoiceReplyManagerService.kt", l = {180, 181}, m = "serializeIncomingIPCs")
/* compiled from: NotificationVoiceReplyManagerService.kt */
public final class NotificationVoiceReplyManagerService$serializeIncomingIPCs$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ NotificationVoiceReplyManagerService this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    NotificationVoiceReplyManagerService$serializeIncomingIPCs$1(NotificationVoiceReplyManagerService notificationVoiceReplyManagerService, Continuation<? super NotificationVoiceReplyManagerService$serializeIncomingIPCs$1> continuation) {
        super(continuation);
        this.this$0 = notificationVoiceReplyManagerService;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.serializeIncomingIPCs(this);
    }
}
