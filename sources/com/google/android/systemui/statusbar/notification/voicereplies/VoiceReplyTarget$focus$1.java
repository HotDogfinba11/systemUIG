package com.google.android.systemui.statusbar.notification.voicereplies;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* access modifiers changed from: package-private */
@DebugMetadata(c = "com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget", f = "NotificationVoiceReplyManager.kt", l = {648, 652, 676, 681, 684}, m = "focus")
/* compiled from: NotificationVoiceReplyManager.kt */
public final class VoiceReplyTarget$focus$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    Object L$4;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ VoiceReplyTarget this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    VoiceReplyTarget$focus$1(VoiceReplyTarget voiceReplyTarget, Continuation<? super VoiceReplyTarget$focus$1> continuation) {
        super(continuation);
        this.this$0 = voiceReplyTarget;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.focus(null, null, null, this);
    }
}
