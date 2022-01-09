package com.google.android.systemui.statusbar.notification.voicereplies;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: Collect.kt */
public final class NotificationVoiceReplyManagerKt$collectLatest$2$invokeSuspend$$inlined$collect$1 implements FlowCollector<T> {
    final /* synthetic */ Function2 $collector$inlined;
    final /* synthetic */ Ref$ObjectRef $job$inlined;
    final /* synthetic */ NotificationVoiceReplyManagerKt$collectLatest$2 this$0;

    public NotificationVoiceReplyManagerKt$collectLatest$2$invokeSuspend$$inlined$collect$1(Ref$ObjectRef ref$ObjectRef, NotificationVoiceReplyManagerKt$collectLatest$2 notificationVoiceReplyManagerKt$collectLatest$2, Function2 function2) {
        this.$job$inlined = ref$ObjectRef;
        this.this$0 = notificationVoiceReplyManagerKt$collectLatest$2;
        this.$collector$inlined = function2;
    }

    @Override // kotlinx.coroutines.flow.FlowCollector
    public Object emit(Object obj, Continuation continuation) {
        T t = this.$job$inlined.element;
        if (t != null) {
            Job.DefaultImpls.cancel$default(t, null, 1, null);
        }
        this.$job$inlined.element = (T) BuildersKt__Builders_commonKt.launch$default(this.this$0.p$, null, null, new NotificationVoiceReplyManagerKt$collectLatest$2$1$1(this.$collector$inlined, obj, null), 3, null);
        return Unit.INSTANCE;
    }
}
