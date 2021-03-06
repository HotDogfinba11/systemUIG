package com.google.android.systemui.statusbar.notification.voicereplies;

import kotlinx.coroutines.flow.StateFlow;

/* compiled from: NotificationVoiceReplyManager.kt */
public interface NotificationVoiceReplyHandler {
    StateFlow<Boolean> getShowCta();

    int getUserId();

    void onNotifAvailableForReplyChanged(boolean z);
}
