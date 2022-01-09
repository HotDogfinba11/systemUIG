package com.google.android.systemui.statusbar;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

/* access modifiers changed from: package-private */
/* compiled from: NotificationVoiceReplyManagerService.kt */
public final class NotificationVoiceReplyManagerService$binder$1$startVoiceReply$3 extends Lambda implements Function0<Unit> {
    final /* synthetic */ CallbacksHandler $handler;
    final /* synthetic */ int $token;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    NotificationVoiceReplyManagerService$binder$1$startVoiceReply$3(CallbacksHandler callbacksHandler, int i) {
        super(0);
        this.$handler = callbacksHandler;
        this.$token = i;
    }

    @Override // kotlin.jvm.functions.Function0
    public final void invoke() {
        this.$handler.onVoiceReplyError(this.$token);
    }
}
