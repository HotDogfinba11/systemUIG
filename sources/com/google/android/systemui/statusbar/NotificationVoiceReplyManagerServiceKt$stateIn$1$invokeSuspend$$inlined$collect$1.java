package com.google.android.systemui.statusbar;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.MutableStateFlow;

/* compiled from: Collect.kt */
public final class NotificationVoiceReplyManagerServiceKt$stateIn$1$invokeSuspend$$inlined$collect$1 implements FlowCollector<T> {
    final /* synthetic */ MutableStateFlow $stateFlow$inlined;

    public NotificationVoiceReplyManagerServiceKt$stateIn$1$invokeSuspend$$inlined$collect$1(MutableStateFlow mutableStateFlow) {
        this.$stateFlow$inlined = mutableStateFlow;
    }

    @Override // kotlinx.coroutines.flow.FlowCollector
    public Object emit(Object obj, Continuation continuation) {
        this.$stateFlow$inlined.setValue(obj);
        return Unit.INSTANCE;
    }
}
