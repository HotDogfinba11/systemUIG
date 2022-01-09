package com.google.android.systemui.statusbar.notification.voicereplies;

import com.android.systemui.statusbar.NotificationRemoteInputManager;

/* access modifiers changed from: package-private */
/* compiled from: NotificationVoiceReplyManager.kt */
public final class VoiceReplyTarget$focus$authBypassCheck$1 implements NotificationRemoteInputManager.AuthBypassPredicate {
    final /* synthetic */ AuthStateRef $authState;

    VoiceReplyTarget$focus$authBypassCheck$1(AuthStateRef authStateRef) {
        this.$authState = authStateRef;
    }

    @Override // com.android.systemui.statusbar.NotificationRemoteInputManager.AuthBypassPredicate
    public final boolean canSendRemoteInputWithoutBouncer() {
        return this.$authState.getValue() == 1;
    }
}
