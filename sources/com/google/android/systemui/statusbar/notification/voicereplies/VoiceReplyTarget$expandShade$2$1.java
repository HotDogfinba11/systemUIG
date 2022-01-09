package com.google.android.systemui.statusbar.notification.voicereplies;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* access modifiers changed from: package-private */
/* compiled from: NotificationVoiceReplyManager.kt */
public final class VoiceReplyTarget$expandShade$2$1 extends Lambda implements Function1<Throwable, Unit> {
    final /* synthetic */ VoiceReplyTarget$expandShade$2$callback$1 $callback;
    final /* synthetic */ VoiceReplyTarget this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    VoiceReplyTarget$expandShade$2$1(VoiceReplyTarget voiceReplyTarget, VoiceReplyTarget$expandShade$2$callback$1 voiceReplyTarget$expandShade$2$callback$1) {
        super(1);
        this.this$0 = voiceReplyTarget;
        this.$callback = voiceReplyTarget$expandShade$2$callback$1;
    }

    /* Return type fixed from 'java.lang.Object' to match base method */
    /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object] */
    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
        invoke(th);
        return Unit.INSTANCE;
    }

    public final void invoke(Throwable th) {
        this.this$0.statusBarStateController.removeCallback(this.$callback);
    }
}
