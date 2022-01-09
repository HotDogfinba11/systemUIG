package com.google.android.systemui.statusbar.notification.voicereplies;

import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController;

/* compiled from: NotificationVoiceReplyManager.kt */
public final class NotificationVoiceReplyController$resetStateOnUserChange$listener$1 implements NotificationLockscreenUserManager.UserChangedListener {
    final /* synthetic */ NotificationVoiceReplyController.Connection $this_resetStateOnUserChange;
    final /* synthetic */ NotificationVoiceReplyController this$0;

    NotificationVoiceReplyController$resetStateOnUserChange$listener$1(NotificationVoiceReplyController.Connection connection, NotificationVoiceReplyController notificationVoiceReplyController) {
        this.$this_resetStateOnUserChange = connection;
        this.this$0 = notificationVoiceReplyController;
    }

    @Override // com.android.systemui.statusbar.NotificationLockscreenUserManager.UserChangedListener
    public void onUserChanged(int i) {
        this.$this_resetStateOnUserChange.getStateFlow().setValue(this.this$0.queryInitialState(this.$this_resetStateOnUserChange));
    }
}
