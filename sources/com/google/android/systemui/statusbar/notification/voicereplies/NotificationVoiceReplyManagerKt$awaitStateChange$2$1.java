package com.google.android.systemui.statusbar.notification.voicereplies;

import com.android.systemui.statusbar.NotificationShadeWindowController;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* access modifiers changed from: package-private */
/* compiled from: NotificationVoiceReplyManager.kt */
public final class NotificationVoiceReplyManagerKt$awaitStateChange$2$1 extends Lambda implements Function1<Throwable, Unit> {
    final /* synthetic */ NotificationVoiceReplyManagerKt$awaitStateChange$2$cb$1 $cb;
    final /* synthetic */ NotificationShadeWindowController $this_awaitStateChange;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    NotificationVoiceReplyManagerKt$awaitStateChange$2$1(NotificationShadeWindowController notificationShadeWindowController, NotificationVoiceReplyManagerKt$awaitStateChange$2$cb$1 notificationVoiceReplyManagerKt$awaitStateChange$2$cb$1) {
        super(1);
        this.$this_awaitStateChange = notificationShadeWindowController;
        this.$cb = notificationVoiceReplyManagerKt$awaitStateChange$2$cb$1;
    }

    /* Return type fixed from 'java.lang.Object' to match base method */
    /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object] */
    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
        invoke(th);
        return Unit.INSTANCE;
    }

    public final void invoke(Throwable th) {
        this.$this_awaitStateChange.unregisterCallback(this.$cb);
    }
}
