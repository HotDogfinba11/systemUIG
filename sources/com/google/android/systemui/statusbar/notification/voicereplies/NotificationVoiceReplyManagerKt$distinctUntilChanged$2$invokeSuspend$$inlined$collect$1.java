package com.google.android.systemui.statusbar.notification.voicereplies;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: Collect.kt */
public final class NotificationVoiceReplyManagerKt$distinctUntilChanged$2$invokeSuspend$$inlined$collect$1 implements FlowCollector<T> {
    final /* synthetic */ Function2 $areEqual$inlined;
    final /* synthetic */ Ref$ObjectRef $prev$inlined;
    final /* synthetic */ NotificationVoiceReplyManagerKt$distinctUntilChanged$2 this$0;

    public NotificationVoiceReplyManagerKt$distinctUntilChanged$2$invokeSuspend$$inlined$collect$1(Ref$ObjectRef ref$ObjectRef, Function2 function2, NotificationVoiceReplyManagerKt$distinctUntilChanged$2 notificationVoiceReplyManagerKt$distinctUntilChanged$2) {
        this.$prev$inlined = ref$ObjectRef;
        this.$areEqual$inlined = function2;
        this.this$0 = notificationVoiceReplyManagerKt$distinctUntilChanged$2;
    }

    /* JADX DEBUG: Multi-variable search result rejected for r3v0, resolved type: java.lang.Object */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlinx.coroutines.flow.FlowCollector
    public Object emit(Object obj, Continuation continuation) {
        T t = this.$prev$inlined.element;
        if (t != NO_VALUE.INSTANCE && ((Boolean) this.$areEqual$inlined.invoke(t, obj)).booleanValue()) {
            return Unit.INSTANCE;
        }
        this.$prev$inlined.element = obj;
        Object emit = this.this$0.p$.emit(obj, continuation);
        return emit == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? emit : Unit.INSTANCE;
    }
}
