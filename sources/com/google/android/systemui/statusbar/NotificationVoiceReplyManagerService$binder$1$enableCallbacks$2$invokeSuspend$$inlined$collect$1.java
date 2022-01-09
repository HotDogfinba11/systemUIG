package com.google.android.systemui.statusbar;

import com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplySubscription;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: Collect.kt */
public final class NotificationVoiceReplyManagerService$binder$1$enableCallbacks$2$invokeSuspend$$inlined$collect$1 implements FlowCollector<StartVoiceReplyData> {
    final /* synthetic */ CallbacksHandler $handler$inlined;
    final /* synthetic */ VoiceReplySubscription $registration$inlined;
    final /* synthetic */ NotificationVoiceReplyManagerService$binder$1$enableCallbacks$2 this$0;
    final /* synthetic */ NotificationVoiceReplyManagerService$binder$1 this$1$inlined;

    public NotificationVoiceReplyManagerService$binder$1$enableCallbacks$2$invokeSuspend$$inlined$collect$1(NotificationVoiceReplyManagerService$binder$1$enableCallbacks$2 notificationVoiceReplyManagerService$binder$1$enableCallbacks$2, NotificationVoiceReplyManagerService$binder$1 notificationVoiceReplyManagerService$binder$1, VoiceReplySubscription voiceReplySubscription, CallbacksHandler callbacksHandler) {
        this.this$0 = notificationVoiceReplyManagerService$binder$1$enableCallbacks$2;
        this.this$1$inlined = notificationVoiceReplyManagerService$binder$1;
        this.$registration$inlined = voiceReplySubscription;
        this.$handler$inlined = callbacksHandler;
    }

    /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object, kotlin.coroutines.Continuation] */
    @Override // kotlinx.coroutines.flow.FlowCollector
    public Object emit(StartVoiceReplyData startVoiceReplyData, Continuation continuation) {
        StartVoiceReplyData startVoiceReplyData2 = startVoiceReplyData;
        Job job = BuildersKt__Builders_commonKt.launch$default(this.this$0.p$, null, null, new NotificationVoiceReplyManagerService$binder$1$enableCallbacks$2$2$1(this.this$1$inlined, this.$registration$inlined, this.$handler$inlined, startVoiceReplyData2.component2(), startVoiceReplyData2.component3(), null), 3, null);
        if (job == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            return job;
        }
        return Unit.INSTANCE;
    }
}
