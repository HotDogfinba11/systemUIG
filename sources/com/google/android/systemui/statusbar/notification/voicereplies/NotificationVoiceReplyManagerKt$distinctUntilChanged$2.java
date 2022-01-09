package com.google.android.systemui.statusbar.notification.voicereplies;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* access modifiers changed from: package-private */
@DebugMetadata(c = "com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyManagerKt$distinctUntilChanged$2", f = "NotificationVoiceReplyManager.kt", l = {1014}, m = "invokeSuspend")
/* compiled from: NotificationVoiceReplyManager.kt */
public final class NotificationVoiceReplyManagerKt$distinctUntilChanged$2 extends SuspendLambda implements Function2<FlowCollector<? super T>, Continuation<? super Unit>, Object> {
    final /* synthetic */ Function2<T, T, Boolean> $areEqual;
    final /* synthetic */ Flow<T> $this_distinctUntilChanged;
    int label;
    private /* synthetic */ FlowCollector<T> p$;

    /* JADX DEBUG: Multi-variable search result rejected for r1v0, resolved type: kotlinx.coroutines.flow.Flow<? extends T> */
    /* JADX DEBUG: Multi-variable search result rejected for r2v0, resolved type: kotlin.jvm.functions.Function2<? super T, ? super T, java.lang.Boolean> */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    NotificationVoiceReplyManagerKt$distinctUntilChanged$2(Flow<? extends T> flow, Function2<? super T, ? super T, Boolean> function2, Continuation<? super NotificationVoiceReplyManagerKt$distinctUntilChanged$2> continuation) {
        super(2, continuation);
        this.$this_distinctUntilChanged = flow;
        this.$areEqual = function2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        NotificationVoiceReplyManagerKt$distinctUntilChanged$2 notificationVoiceReplyManagerKt$distinctUntilChanged$2 = new NotificationVoiceReplyManagerKt$distinctUntilChanged$2(this.$this_distinctUntilChanged, this.$areEqual, continuation);
        notificationVoiceReplyManagerKt$distinctUntilChanged$2.p$ = (FlowCollector) obj;
        return notificationVoiceReplyManagerKt$distinctUntilChanged$2;
    }

    public final Object invoke(FlowCollector<? super T> flowCollector, Continuation<? super Unit> continuation) {
        return ((NotificationVoiceReplyManagerKt$distinctUntilChanged$2) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object obj2 = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
            ref$ObjectRef.element = (T) NO_VALUE.INSTANCE;
            Flow<T> flow = this.$this_distinctUntilChanged;
            NotificationVoiceReplyManagerKt$distinctUntilChanged$2$invokeSuspend$$inlined$collect$1 notificationVoiceReplyManagerKt$distinctUntilChanged$2$invokeSuspend$$inlined$collect$1 = new NotificationVoiceReplyManagerKt$distinctUntilChanged$2$invokeSuspend$$inlined$collect$1(ref$ObjectRef, this.$areEqual, this);
            this.label = 1;
            if (flow.collect(notificationVoiceReplyManagerKt$distinctUntilChanged$2$invokeSuspend$$inlined$collect$1, this) == obj2) {
                return obj2;
            }
        } else if (i == 1) {
            ResultKt.throwOnFailure(obj);
        } else {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        return Unit.INSTANCE;
    }
}
