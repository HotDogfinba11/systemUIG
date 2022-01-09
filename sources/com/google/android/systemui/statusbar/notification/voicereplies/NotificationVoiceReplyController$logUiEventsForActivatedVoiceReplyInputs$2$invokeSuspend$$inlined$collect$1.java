package com.google.android.systemui.statusbar.notification.voicereplies;

import com.android.systemui.statusbar.policy.RemoteInputView;
import com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController;
import kotlin.Pair;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: Collect.kt */
public final class NotificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2$invokeSuspend$$inlined$collect$1 implements FlowCollector<Pair<? extends String, ? extends RemoteInputView>> {
    final /* synthetic */ Flow $remoteInputViewActivatedForVoiceReply$inlined;
    final /* synthetic */ NotificationVoiceReplyController.Connection $this_logUiEventsForActivatedVoiceReplyInputs$inlined;
    final /* synthetic */ NotificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2 this$0;
    final /* synthetic */ NotificationVoiceReplyController this$1$inlined;

    public NotificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2$invokeSuspend$$inlined$collect$1(NotificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2 notificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2, NotificationVoiceReplyController.Connection connection, NotificationVoiceReplyController notificationVoiceReplyController, Flow flow) {
        this.this$0 = notificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2;
        this.$this_logUiEventsForActivatedVoiceReplyInputs$inlined = connection;
        this.this$1$inlined = notificationVoiceReplyController;
        this.$remoteInputViewActivatedForVoiceReply$inlined = flow;
    }

    /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object, kotlin.coroutines.Continuation] */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0043  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0028  */
    @Override // kotlinx.coroutines.flow.FlowCollector
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object emit(kotlin.Pair<? extends java.lang.String, ? extends com.android.systemui.statusbar.policy.RemoteInputView> r19, kotlin.coroutines.Continuation r20) {
        /*
        // Method dump skipped, instructions count: 195
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2$invokeSuspend$$inlined$collect$1.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
