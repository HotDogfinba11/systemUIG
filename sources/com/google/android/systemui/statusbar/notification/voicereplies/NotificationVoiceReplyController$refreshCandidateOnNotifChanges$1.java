package com.google.android.systemui.statusbar.notification.voicereplies;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* access modifiers changed from: package-private */
@DebugMetadata(c = "com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController", f = "NotificationVoiceReplyManager.kt", l = {1017}, m = "refreshCandidateOnNotifChanges")
/* compiled from: NotificationVoiceReplyManager.kt */
public final class NotificationVoiceReplyController$refreshCandidateOnNotifChanges$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ NotificationVoiceReplyController this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    NotificationVoiceReplyController$refreshCandidateOnNotifChanges$1(NotificationVoiceReplyController notificationVoiceReplyController, Continuation<? super NotificationVoiceReplyController$refreshCandidateOnNotifChanges$1> continuation) {
        super(continuation);
        this.this$0 = notificationVoiceReplyController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.refreshCandidateOnNotifChanges(null, this);
    }
}
