package com.google.android.systemui.statusbar.notification.voicereplies;

import java.util.Iterator;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CancellableContinuationImpl;

/* access modifiers changed from: package-private */
@DebugMetadata(c = "com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$notifyHandlersOfReplyAvailability$4", f = "NotificationVoiceReplyManager.kt", l = {1019}, m = "invokeSuspend")
/* compiled from: NotificationVoiceReplyManager.kt */
public final class NotificationVoiceReplyController$notifyHandlersOfReplyAvailability$4 extends SuspendLambda implements Function2<VoiceReplyTarget, Continuation<? super Unit>, Object> {
    Object L$0;
    /* synthetic */ VoiceReplyTarget candidate;
    int label;
    final /* synthetic */ NotificationVoiceReplyController this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    NotificationVoiceReplyController$notifyHandlersOfReplyAvailability$4(NotificationVoiceReplyController notificationVoiceReplyController, Continuation<? super NotificationVoiceReplyController$notifyHandlersOfReplyAvailability$4> continuation) {
        super(2, continuation);
        this.this$0 = notificationVoiceReplyController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        NotificationVoiceReplyController$notifyHandlersOfReplyAvailability$4 notificationVoiceReplyController$notifyHandlersOfReplyAvailability$4 = new NotificationVoiceReplyController$notifyHandlersOfReplyAvailability$4(this.this$0, continuation);
        notificationVoiceReplyController$notifyHandlersOfReplyAvailability$4.candidate = (VoiceReplyTarget) obj;
        return notificationVoiceReplyController$notifyHandlersOfReplyAvailability$4;
    }

    public final Object invoke(VoiceReplyTarget voiceReplyTarget, Continuation<? super Unit> continuation) {
        return ((NotificationVoiceReplyController$notifyHandlersOfReplyAvailability$4) create(voiceReplyTarget, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Throwable th;
        NotificationVoiceReplyController notificationVoiceReplyController;
        Object obj2 = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            if (this.candidate == null) {
                return Unit.INSTANCE;
            }
            this.this$0.logger.logCandidateUserChange(this.candidate.getUserId(), true);
            Iterator<T> it = this.candidate.getHandlers().iterator();
            while (it.hasNext()) {
                it.next().onNotifAvailableForReplyChanged(true);
            }
            NotificationVoiceReplyController notificationVoiceReplyController2 = this.this$0;
            try {
                this.L$0 = notificationVoiceReplyController2;
                this.label = 1;
                Object result = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(this), 1).getResult();
                if (result == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                    DebugProbesKt.probeCoroutineSuspended(this);
                }
                if (result == obj2) {
                    return obj2;
                }
            } catch (Throwable th2) {
                notificationVoiceReplyController = notificationVoiceReplyController2;
                th = th2;
                notificationVoiceReplyController.logger.logCandidateUserChange(this.candidate.getUserId(), false);
                Iterator<T> it2 = this.candidate.getHandlers().iterator();
                while (it2.hasNext()) {
                    it2.next().onNotifAvailableForReplyChanged(false);
                }
                throw th;
            }
        } else if (i != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        } else {
            notificationVoiceReplyController = (NotificationVoiceReplyController) this.L$0;
            try {
                ResultKt.throwOnFailure(obj);
            } catch (Throwable th3) {
                th = th3;
            }
        }
        throw new KotlinNothingValueException();
    }
}
