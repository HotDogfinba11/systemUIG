package com.google.android.systemui.statusbar.notification.voicereplies;

import kotlin.jvm.internal.Ref$BooleanRef;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: NotificationVoiceReplyManager.kt */
public final class DebugNotificationVoiceReplyClient$startClient$job$1$subscription$1 implements NotificationVoiceReplyHandler {
    final /* synthetic */ Ref$BooleanRef $notifAvailable;
    private final StateFlow<Boolean> showCta = StateFlowKt.MutableStateFlow(Boolean.TRUE);
    final /* synthetic */ DebugNotificationVoiceReplyClient this$0;
    private final int userId;

    DebugNotificationVoiceReplyClient$startClient$job$1$subscription$1(DebugNotificationVoiceReplyClient debugNotificationVoiceReplyClient, Ref$BooleanRef ref$BooleanRef) {
        this.this$0 = debugNotificationVoiceReplyClient;
        this.$notifAvailable = ref$BooleanRef;
        this.userId = debugNotificationVoiceReplyClient.lockscreenUserManager.getCurrentUserId();
    }

    @Override // com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyHandler
    public int getUserId() {
        return this.userId;
    }

    @Override // com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyHandler
    public StateFlow<Boolean> getShowCta() {
        return this.showCta;
    }

    @Override // com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyHandler
    public void onNotifAvailableForReplyChanged(boolean z) {
        this.$notifAvailable.element = z;
    }
}
