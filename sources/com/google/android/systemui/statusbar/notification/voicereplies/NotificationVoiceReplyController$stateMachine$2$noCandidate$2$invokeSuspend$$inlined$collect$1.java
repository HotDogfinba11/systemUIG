package com.google.android.systemui.statusbar.notification.voicereplies;

import com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: Collect.kt */
public final class NotificationVoiceReplyController$stateMachine$2$noCandidate$2$invokeSuspend$$inlined$collect$1 implements FlowCollector<VoiceReplyTarget> {
    final /* synthetic */ NotificationVoiceReplyController.Connection $this_stateMachine$inlined;

    public NotificationVoiceReplyController$stateMachine$2$noCandidate$2$invokeSuspend$$inlined$collect$1(NotificationVoiceReplyController.Connection connection) {
        this.$this_stateMachine$inlined = connection;
    }

    /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object, kotlin.coroutines.Continuation] */
    @Override // kotlinx.coroutines.flow.FlowCollector
    public Object emit(VoiceReplyTarget voiceReplyTarget, Continuation continuation) {
        this.$this_stateMachine$inlined.getStateFlow().setValue(new HasCandidate(voiceReplyTarget));
        return Unit.INSTANCE;
    }
}
