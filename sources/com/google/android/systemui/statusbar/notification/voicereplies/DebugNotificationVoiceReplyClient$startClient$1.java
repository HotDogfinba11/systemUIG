package com.google.android.systemui.statusbar.notification.voicereplies;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import kotlinx.coroutines.Job;

/* compiled from: NotificationVoiceReplyManager.kt */
final class DebugNotificationVoiceReplyClient$startClient$1 extends Lambda implements Function0<Unit> {
    final /* synthetic */ Job $job;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    DebugNotificationVoiceReplyClient$startClient$1(Job job) {
        super(0);
        this.$job = job;
    }

    @Override // kotlin.jvm.functions.Function0
    public final void invoke() {
        Job.DefaultImpls.cancel$default(this.$job, null, 1, null);
    }
}
