package com.google.android.systemui.statusbar.notification.voicereplies;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;

/* access modifiers changed from: package-private */
@DebugMetadata(c = "com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyManagerKt$collectLatest$2", f = "NotificationVoiceReplyManager.kt", l = {1014}, m = "invokeSuspend")
/* compiled from: NotificationVoiceReplyManager.kt */
public final class NotificationVoiceReplyManagerKt$collectLatest$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ Function2<T, Continuation<? super Unit>, Object> $collector;
    final /* synthetic */ Flow<T> $this_collectLatest;
    int label;
    private /* synthetic */ CoroutineScope p$;

    /* JADX DEBUG: Multi-variable search result rejected for r1v0, resolved type: kotlinx.coroutines.flow.Flow<? extends T> */
    /* JADX DEBUG: Multi-variable search result rejected for r2v0, resolved type: kotlin.jvm.functions.Function2<? super T, ? super kotlin.coroutines.Continuation<? super kotlin.Unit>, ? extends java.lang.Object> */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    NotificationVoiceReplyManagerKt$collectLatest$2(Flow<? extends T> flow, Function2<? super T, ? super Continuation<? super Unit>, ? extends Object> function2, Continuation<? super NotificationVoiceReplyManagerKt$collectLatest$2> continuation) {
        super(2, continuation);
        this.$this_collectLatest = flow;
        this.$collector = function2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        NotificationVoiceReplyManagerKt$collectLatest$2 notificationVoiceReplyManagerKt$collectLatest$2 = new NotificationVoiceReplyManagerKt$collectLatest$2(this.$this_collectLatest, this.$collector, continuation);
        notificationVoiceReplyManagerKt$collectLatest$2.p$ = (CoroutineScope) obj;
        return notificationVoiceReplyManagerKt$collectLatest$2;
    }

    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((NotificationVoiceReplyManagerKt$collectLatest$2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object obj2 = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
            Flow<T> flow = this.$this_collectLatest;
            NotificationVoiceReplyManagerKt$collectLatest$2$invokeSuspend$$inlined$collect$1 notificationVoiceReplyManagerKt$collectLatest$2$invokeSuspend$$inlined$collect$1 = new NotificationVoiceReplyManagerKt$collectLatest$2$invokeSuspend$$inlined$collect$1(ref$ObjectRef, this, this.$collector);
            this.label = 1;
            if (flow.collect(notificationVoiceReplyManagerKt$collectLatest$2$invokeSuspend$$inlined$collect$1, this) == obj2) {
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
