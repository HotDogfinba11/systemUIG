package com.google.android.systemui.statusbar.notification.voicereplies;

import com.android.systemui.statusbar.policy.HeadsUpManager;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* access modifiers changed from: package-private */
/* compiled from: NotificationVoiceReplyManager.kt */
public final class NotificationVoiceReplyManagerKt$awaitHunStateChange$2$1 extends Lambda implements Function1<Throwable, Unit> {
    final /* synthetic */ NotificationVoiceReplyManagerKt$awaitHunStateChange$2$listener$1 $listener;
    final /* synthetic */ HeadsUpManager $this_awaitHunStateChange;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    NotificationVoiceReplyManagerKt$awaitHunStateChange$2$1(HeadsUpManager headsUpManager, NotificationVoiceReplyManagerKt$awaitHunStateChange$2$listener$1 notificationVoiceReplyManagerKt$awaitHunStateChange$2$listener$1) {
        super(1);
        this.$this_awaitHunStateChange = headsUpManager;
        this.$listener = notificationVoiceReplyManagerKt$awaitHunStateChange$2$listener$1;
    }

    /* Return type fixed from 'java.lang.Object' to match base method */
    /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object] */
    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
        invoke(th);
        return Unit.INSTANCE;
    }

    public final void invoke(Throwable th) {
        this.$this_awaitHunStateChange.removeListener(this.$listener);
    }
}
