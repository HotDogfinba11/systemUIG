package com.google.android.systemui.statusbar.notification.voicereplies;

import com.android.systemui.statusbar.policy.RemoteInputView;
import com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CompletableDeferred;

/* compiled from: NotificationVoiceReplyManager.kt */
public final class NotificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2$1$sendEventJob$1$listener$1 implements RemoteInputView.OnSendRemoteInputListener {
    final /* synthetic */ CompletableDeferred<Unit> $completion;
    final /* synthetic */ String $key;
    final /* synthetic */ NotificationVoiceReplyController.Connection $this_logUiEventsForActivatedVoiceReplyInputs;
    final /* synthetic */ NotificationVoiceReplyController this$0;

    NotificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2$1$sendEventJob$1$listener$1(NotificationVoiceReplyController.Connection connection, String str, NotificationVoiceReplyController notificationVoiceReplyController, CompletableDeferred<Unit> completableDeferred) {
        this.$this_logUiEventsForActivatedVoiceReplyInputs = connection;
        this.$key = str;
        this.this$0 = notificationVoiceReplyController;
        this.$completion = completableDeferred;
    }

    @Override // com.android.systemui.statusbar.policy.RemoteInputView.OnSendRemoteInputListener
    public void onSendRemoteInput() {
        VoiceReplyState value = this.$this_logUiEventsForActivatedVoiceReplyInputs.getStateFlow().getValue();
        String str = null;
        InSession inSession = value instanceof InSession ? (InSession) value : null;
        if (inSession != null) {
            str = inSession.getTarget().getNotifKey();
        }
        if (!Intrinsics.areEqual(str, this.$key)) {
            this.this$0.logger.logMsgSentDelayed(this.$key);
        } else if (this.this$0.statusBarStateController.getState() == 2) {
            this.this$0.logger.logMsgSentAuthBypassed(this.$key);
        } else {
            this.this$0.logger.logMsgSentUnlocked(this.$key);
        }
        this.$completion.complete(Unit.INSTANCE);
    }

    @Override // com.android.systemui.statusbar.policy.RemoteInputView.OnSendRemoteInputListener
    public void onSendRequestBounced() {
        this.this$0.logger.logMsgSendBounced(this.$key);
    }
}
