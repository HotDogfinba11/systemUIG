package com.google.android.systemui.statusbar;

import com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyManager;
import com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplySubscription;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* access modifiers changed from: package-private */
@DebugMetadata(c = "com.google.android.systemui.statusbar.NotificationVoiceReplyManagerService$binder$1$enableCallbacks$2", f = "NotificationVoiceReplyManagerService.kt", l = {273}, m = "invokeSuspend")
/* compiled from: NotificationVoiceReplyManagerService.kt */
public final class NotificationVoiceReplyManagerService$binder$1$enableCallbacks$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ INotificationVoiceReplyServiceCallbacks $callbacks;
    final /* synthetic */ boolean $showCTA;
    Object L$0;
    int label;
    private /* synthetic */ CoroutineScope p$;
    final /* synthetic */ NotificationVoiceReplyManagerService$binder$1 this$0;
    final /* synthetic */ NotificationVoiceReplyManagerService this$1;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    NotificationVoiceReplyManagerService$binder$1$enableCallbacks$2(NotificationVoiceReplyManagerService$binder$1 notificationVoiceReplyManagerService$binder$1, boolean z, INotificationVoiceReplyServiceCallbacks iNotificationVoiceReplyServiceCallbacks, NotificationVoiceReplyManagerService notificationVoiceReplyManagerService, Continuation<? super NotificationVoiceReplyManagerService$binder$1$enableCallbacks$2> continuation) {
        super(2, continuation);
        this.this$0 = notificationVoiceReplyManagerService$binder$1;
        this.$showCTA = z;
        this.$callbacks = iNotificationVoiceReplyServiceCallbacks;
        this.this$1 = notificationVoiceReplyManagerService;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        NotificationVoiceReplyManagerService$binder$1$enableCallbacks$2 notificationVoiceReplyManagerService$binder$1$enableCallbacks$2 = new NotificationVoiceReplyManagerService$binder$1$enableCallbacks$2(this.this$0, this.$showCTA, this.$callbacks, this.this$1, continuation);
        notificationVoiceReplyManagerService$binder$1$enableCallbacks$2.p$ = (CoroutineScope) obj;
        return notificationVoiceReplyManagerService$binder$1$enableCallbacks$2;
    }

    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((NotificationVoiceReplyManagerService$binder$1$enableCallbacks$2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Throwable th;
        VoiceReplySubscription voiceReplySubscription;
        Object obj2 = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CallbacksHandler callbacksHandler = new CallbacksHandler(this.this$0.getUserId(), this.$callbacks, NotificationVoiceReplyManagerServiceKt.stateIn(new NotificationVoiceReplyManagerService$binder$1$enableCallbacks$2$invokeSuspend$$inlined$map$1(new NotificationVoiceReplyManagerService$binder$1$enableCallbacks$2$invokeSuspend$$inlined$filter$1(this.this$0.getSetFeatureEnabledFlow(), this.this$0)), this.p$, Boxing.boxBoolean(this.$showCTA)));
            NotificationVoiceReplyManager notificationVoiceReplyManager = this.this$1.voiceReplyManager;
            if (notificationVoiceReplyManager != null) {
                VoiceReplySubscription registerHandler = notificationVoiceReplyManager.registerHandler(callbacksHandler);
                try {
                    NotificationVoiceReplyManagerService$binder$1$enableCallbacks$2$invokeSuspend$$inlined$filter$2 notificationVoiceReplyManagerService$binder$1$enableCallbacks$2$invokeSuspend$$inlined$filter$2 = new NotificationVoiceReplyManagerService$binder$1$enableCallbacks$2$invokeSuspend$$inlined$filter$2(this.this$0.getStartVoiceReplyFlow(), this.this$0);
                    NotificationVoiceReplyManagerService$binder$1$enableCallbacks$2$invokeSuspend$$inlined$collect$1 notificationVoiceReplyManagerService$binder$1$enableCallbacks$2$invokeSuspend$$inlined$collect$1 = new NotificationVoiceReplyManagerService$binder$1$enableCallbacks$2$invokeSuspend$$inlined$collect$1(this, this.this$0, registerHandler, callbacksHandler);
                    this.L$0 = registerHandler;
                    this.label = 1;
                    if (notificationVoiceReplyManagerService$binder$1$enableCallbacks$2$invokeSuspend$$inlined$filter$2.collect(notificationVoiceReplyManagerService$binder$1$enableCallbacks$2$invokeSuspend$$inlined$collect$1, this) == obj2) {
                        return obj2;
                    }
                    voiceReplySubscription = registerHandler;
                } catch (Throwable th2) {
                    voiceReplySubscription = registerHandler;
                    th = th2;
                    this.this$1.logger.logUnregisterCallbacks(this.this$0.getUserId());
                    voiceReplySubscription.unsubscribe();
                    throw th;
                }
            } else {
                Intrinsics.throwUninitializedPropertyAccessException("voiceReplyManager");
                throw null;
            }
        } else if (i == 1) {
            voiceReplySubscription = (VoiceReplySubscription) this.L$0;
            try {
                ResultKt.throwOnFailure(obj);
            } catch (Throwable th3) {
                th = th3;
            }
        } else {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        this.this$1.logger.logUnregisterCallbacks(this.this$0.getUserId());
        voiceReplySubscription.unsubscribe();
        return Unit.INSTANCE;
    }
}
