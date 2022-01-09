package com.google.android.systemui.statusbar;

import kotlin.Pair;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: SafeCollector.kt */
public final class NotificationVoiceReplyManagerService$binder$1$registerCallbacksWhenEnabled$$inlined$filter$1 implements Flow<Pair<? extends Integer, ? extends Integer>> {
    final /* synthetic */ Flow $this_unsafeTransform$inlined;
    final /* synthetic */ NotificationVoiceReplyManagerService$binder$1 this$0;

    public NotificationVoiceReplyManagerService$binder$1$registerCallbacksWhenEnabled$$inlined$filter$1(Flow flow, NotificationVoiceReplyManagerService$binder$1 notificationVoiceReplyManagerService$binder$1) {
        this.$this_unsafeTransform$inlined = flow;
        this.this$0 = notificationVoiceReplyManagerService$binder$1;
    }

    /* JADX DEBUG: Method arguments types fixed to match base method, original types: [kotlinx.coroutines.flow.FlowCollector, kotlin.coroutines.Continuation] */
    @Override // kotlinx.coroutines.flow.Flow
    public Object collect(final FlowCollector<? super Pair<? extends Integer, ? extends Integer>> flowCollector, Continuation continuation) {
        Object collect = this.$this_unsafeTransform$inlined.collect(new FlowCollector<Pair<? extends Integer, ? extends Integer>>() {
            /* class com.google.android.systemui.statusbar.NotificationVoiceReplyManagerService$binder$1$registerCallbacksWhenEnabled$$inlined$filter$1.AnonymousClass2 */

            /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object, kotlin.coroutines.Continuation] */
            @Override // kotlinx.coroutines.flow.FlowCollector
            public Object emit(Pair<? extends Integer, ? extends Integer> pair, Continuation continuation) {
                FlowCollector flowCollector = flowCollector;
                if (!Boxing.boxBoolean(((Number) pair.getFirst()).intValue() == this.this$0.getUserId()).booleanValue()) {
                    return Unit.INSTANCE;
                }
                Object emit = flowCollector.emit(pair, continuation);
                return emit == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? emit : Unit.INSTANCE;
            }
        }, continuation);
        return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
    }
}
