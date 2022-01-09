package com.google.android.systemui.statusbar.notification.voicereplies;

/* compiled from: NotificationVoiceReplyManager.kt */
public final class NotificationVoiceReplyController$stateMachine$2$inSession$2$1$session$1 implements Session {
    final /* synthetic */ AuthStateRef $authState;

    NotificationVoiceReplyController$stateMachine$2$inSession$2$1$session$1(AuthStateRef authStateRef) {
        this.$authState = authStateRef;
    }

    @Override // com.google.android.systemui.statusbar.notification.voicereplies.Session
    public void setVoiceAuthState(int i) {
        this.$authState.setValue(i);
    }
}
