package com.google.android.systemui.statusbar;

import android.os.Binder;
import android.os.UserHandle;
import com.google.android.systemui.statusbar.INotificationVoiceReplyService;
import com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyManagerKt;
import com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplySubscription;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.flow.MutableSharedFlow;
import kotlinx.coroutines.flow.SharedFlowKt;

/* compiled from: NotificationVoiceReplyManagerService.kt */
public final class NotificationVoiceReplyManagerService$binder$1 extends INotificationVoiceReplyService.Stub {
    private final MutableSharedFlow<OnVoiceAuthStateChangedData> onVoiceAuthStateChangedFlow = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7, null);
    private final MutableSharedFlow<Pair<Integer, Integer>> setFeatureEnabledFlow = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7, null);
    private final MutableSharedFlow<StartVoiceReplyData> startVoiceReplyFlow = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7, null);
    final /* synthetic */ NotificationVoiceReplyManagerService this$0;

    NotificationVoiceReplyManagerService$binder$1(NotificationVoiceReplyManagerService notificationVoiceReplyManagerService) {
        this.this$0 = notificationVoiceReplyManagerService;
    }

    public final int getUserId() {
        return UserHandle.getUserId(Binder.getCallingUid());
    }

    public final MutableSharedFlow<Pair<Integer, Integer>> getSetFeatureEnabledFlow() {
        return this.setFeatureEnabledFlow;
    }

    public final MutableSharedFlow<StartVoiceReplyData> getStartVoiceReplyFlow() {
        return this.startVoiceReplyFlow;
    }

    public final MutableSharedFlow<OnVoiceAuthStateChangedData> getOnVoiceAuthStateChangedFlow() {
        return this.onVoiceAuthStateChangedFlow;
    }

    @Override // com.google.android.systemui.statusbar.INotificationVoiceReplyService
    public void registerCallbacks(INotificationVoiceReplyServiceCallbacks iNotificationVoiceReplyServiceCallbacks) {
        Intrinsics.checkNotNullParameter(iNotificationVoiceReplyServiceCallbacks, "callbacks");
        this.this$0.ensureCallerIsAgsa();
        NotificationVoiceReplyManagerService notificationVoiceReplyManagerService = this.this$0;
        notificationVoiceReplyManagerService.serially(new NotificationVoiceReplyManagerService$binder$1$registerCallbacks$1(notificationVoiceReplyManagerService, this, iNotificationVoiceReplyServiceCallbacks, null));
    }

    @Override // com.google.android.systemui.statusbar.INotificationVoiceReplyService
    public void setFeatureEnabled(int i) {
        this.this$0.ensureCallerIsAgsa();
        NotificationVoiceReplyManagerService notificationVoiceReplyManagerService = this.this$0;
        notificationVoiceReplyManagerService.serially(new NotificationVoiceReplyManagerService$binder$1$setFeatureEnabled$1(notificationVoiceReplyManagerService, this, i, null));
    }

    @Override // com.google.android.systemui.statusbar.INotificationVoiceReplyService
    public void startVoiceReply(int i, String str) {
        this.this$0.ensureCallerIsAgsa();
        NotificationVoiceReplyManagerService notificationVoiceReplyManagerService = this.this$0;
        notificationVoiceReplyManagerService.serially(new NotificationVoiceReplyManagerService$binder$1$startVoiceReply$1(notificationVoiceReplyManagerService, this, i, str, null));
    }

    @Override // com.google.android.systemui.statusbar.INotificationVoiceReplyService
    public void onVoiceAuthStateChanged(int i, int i2) {
        this.this$0.ensureCallerIsAgsa();
        NotificationVoiceReplyManagerService notificationVoiceReplyManagerService = this.this$0;
        notificationVoiceReplyManagerService.serially(new NotificationVoiceReplyManagerService$binder$1$onVoiceAuthStateChanged$1(notificationVoiceReplyManagerService, this, i, i2, null));
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ Object registerCallbacksWhenEnabled(INotificationVoiceReplyServiceCallbacks iNotificationVoiceReplyServiceCallbacks, Continuation continuation) {
        Object collectLatest = NotificationVoiceReplyManagerKt.collectLatest(NotificationVoiceReplyManagerKt.distinctUntilChanged(NotificationVoiceReplyManagerServiceKt.access$startingWith(new NotificationVoiceReplyManagerService$binder$1$registerCallbacksWhenEnabled$$inlined$filter$1(getSetFeatureEnabledFlow(), this), TuplesKt.to(Boxing.boxInt(getUserId()), Boxing.boxInt(2))), NotificationVoiceReplyManagerService$binder$1$registerCallbacksWhenEnabled$3.INSTANCE), new NotificationVoiceReplyManagerService$binder$1$registerCallbacksWhenEnabled$4(this, iNotificationVoiceReplyServiceCallbacks, null), continuation);
        return collectLatest == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collectLatest : Unit.INSTANCE;
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ Object enableCallbacks(INotificationVoiceReplyServiceCallbacks iNotificationVoiceReplyServiceCallbacks, boolean z, Continuation continuation) {
        Object coroutineScope = CoroutineScopeKt.coroutineScope(new NotificationVoiceReplyManagerService$binder$1$enableCallbacks$2(this, z, iNotificationVoiceReplyServiceCallbacks, this.this$0, null), continuation);
        return coroutineScope == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? coroutineScope : Unit.INSTANCE;
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ Object startVoiceReply(VoiceReplySubscription voiceReplySubscription, CallbacksHandler callbacksHandler, int i, String str, Continuation continuation) {
        Object startVoiceReply = voiceReplySubscription.startVoiceReply(i, str, new NotificationVoiceReplyManagerService$binder$1$startVoiceReply$3(callbacksHandler, i), new NotificationVoiceReplyManagerService$binder$1$startVoiceReply$4(callbacksHandler, i, this, null), continuation);
        return startVoiceReply == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? startVoiceReply : Unit.INSTANCE;
    }
}
