package com.google.android.systemui.statusbar;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: Collect.kt */
public final class NotificationVoiceReplyManagerServiceKt$startingWith$1$1$invokeSuspend$$inlined$collect$1 implements FlowCollector<T> {
    final /* synthetic */ FlowCollector $receiver$inlined;

    public NotificationVoiceReplyManagerServiceKt$startingWith$1$1$invokeSuspend$$inlined$collect$1(FlowCollector flowCollector) {
        this.$receiver$inlined = flowCollector;
    }

    @Override // kotlinx.coroutines.flow.FlowCollector
    public Object emit(Object obj, Continuation continuation) {
        Object emit = this.$receiver$inlined.emit(obj, continuation);
        return emit == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? emit : Unit.INSTANCE;
    }
}
