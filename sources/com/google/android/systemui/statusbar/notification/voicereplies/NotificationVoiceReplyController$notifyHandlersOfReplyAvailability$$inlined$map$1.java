package com.google.android.systemui.statusbar.notification.voicereplies;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: SafeCollector.kt */
public final class NotificationVoiceReplyController$notifyHandlersOfReplyAvailability$$inlined$map$1 implements Flow<VoiceReplyTarget> {
    final /* synthetic */ Flow $this_unsafeTransform$inlined;

    public NotificationVoiceReplyController$notifyHandlersOfReplyAvailability$$inlined$map$1(Flow flow) {
        this.$this_unsafeTransform$inlined = flow;
    }

    /* JADX DEBUG: Method arguments types fixed to match base method, original types: [kotlinx.coroutines.flow.FlowCollector, kotlin.coroutines.Continuation] */
    @Override // kotlinx.coroutines.flow.Flow
    public Object collect(final FlowCollector<? super VoiceReplyTarget> flowCollector, Continuation continuation) {
        Object collect = this.$this_unsafeTransform$inlined.collect(new FlowCollector<VoiceReplyState>() {
            /* class com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$notifyHandlersOfReplyAvailability$$inlined$map$1.AnonymousClass2 */

            /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object, kotlin.coroutines.Continuation] */
            @Override // kotlinx.coroutines.flow.FlowCollector
            public Object emit(VoiceReplyState voiceReplyState, Continuation continuation) {
                FlowCollector flowCollector = flowCollector;
                VoiceReplyState voiceReplyState2 = voiceReplyState;
                VoiceReplyTarget voiceReplyTarget = null;
                HasCandidate hasCandidate = voiceReplyState2 instanceof HasCandidate ? (HasCandidate) voiceReplyState2 : null;
                if (hasCandidate != null) {
                    voiceReplyTarget = hasCandidate.getCandidate();
                }
                Object emit = flowCollector.emit(voiceReplyTarget, continuation);
                return emit == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? emit : Unit.INSTANCE;
            }
        }, continuation);
        return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
    }
}
