package com.google.android.systemui.statusbar.notification.voicereplies;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.widget.Button;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.policy.RemoteInputView;
import java.util.List;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancellableContinuationImpl;

/* access modifiers changed from: package-private */
/* compiled from: NotificationVoiceReplyManager.kt */
public final class VoiceReplyTarget {
    private final PendingIntent actionIntent;
    private final Notification.Builder builder;
    private final NotificationEntry entry;
    private final Button expandedViewReplyButton;
    private final RemoteInput freeformInput;
    private final List<NotificationVoiceReplyHandler> handlers;
    private final NotificationVoiceReplyLogger logger;
    private final String notifKey;
    private final NotificationShadeWindowController notifShadeWindowController;
    private final NotificationRemoteInputManager notificationRemoteInputManager;
    private final long postTime;
    private final RemoteInput[] remoteInputs;
    private final LockscreenShadeTransitionController shadeTransitionController;
    private final StatusBar statusBar;
    private final StatusBarKeyguardViewManager statusBarKeyguardViewManager;
    private final SysuiStatusBarStateController statusBarStateController;
    private final int userId;

    /* access modifiers changed from: package-private */
    public final /* synthetic */ Object awaitKeyguardReset(Continuation continuation) {
        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(continuation), 1);
        VoiceReplyTarget$awaitKeyguardReset$2$callback$1 voiceReplyTarget$awaitKeyguardReset$2$callback$1 = new VoiceReplyTarget$awaitKeyguardReset$2$callback$1(NotificationVoiceReplyManagerKt.AtomicLatch(), this, cancellableContinuationImpl);
        this.statusBarKeyguardViewManager.getBouncer().addKeyguardResetCallback(voiceReplyTarget$awaitKeyguardReset$2$callback$1);
        cancellableContinuationImpl.invokeOnCancellation(new VoiceReplyTarget$awaitKeyguardReset$2$1(this, voiceReplyTarget$awaitKeyguardReset$2$callback$1));
        Object result = cancellableContinuationImpl.getResult();
        if (result == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return result == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? result : Unit.INSTANCE;
    }

    /* JADX DEBUG: Multi-variable search result rejected for r21v0, resolved type: java.util.List<? extends com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyHandler> */
    /* JADX WARN: Multi-variable type inference failed */
    public VoiceReplyTarget(NotificationEntry notificationEntry, Notification.Builder builder2, long j, List<? extends NotificationVoiceReplyHandler> list, PendingIntent pendingIntent, RemoteInput[] remoteInputArr, RemoteInput remoteInput, Button button, NotificationRemoteInputManager notificationRemoteInputManager2, LockscreenShadeTransitionController lockscreenShadeTransitionController, StatusBar statusBar2, SysuiStatusBarStateController sysuiStatusBarStateController, NotificationVoiceReplyLogger notificationVoiceReplyLogger, NotificationShadeWindowController notificationShadeWindowController, StatusBarKeyguardViewManager statusBarKeyguardViewManager2) {
        Intrinsics.checkNotNullParameter(notificationEntry, "entry");
        Intrinsics.checkNotNullParameter(builder2, "builder");
        Intrinsics.checkNotNullParameter(list, "handlers");
        Intrinsics.checkNotNullParameter(pendingIntent, "actionIntent");
        Intrinsics.checkNotNullParameter(remoteInputArr, "remoteInputs");
        Intrinsics.checkNotNullParameter(remoteInput, "freeformInput");
        Intrinsics.checkNotNullParameter(button, "expandedViewReplyButton");
        Intrinsics.checkNotNullParameter(notificationRemoteInputManager2, "notificationRemoteInputManager");
        Intrinsics.checkNotNullParameter(lockscreenShadeTransitionController, "shadeTransitionController");
        Intrinsics.checkNotNullParameter(statusBar2, "statusBar");
        Intrinsics.checkNotNullParameter(sysuiStatusBarStateController, "statusBarStateController");
        Intrinsics.checkNotNullParameter(notificationVoiceReplyLogger, "logger");
        Intrinsics.checkNotNullParameter(notificationShadeWindowController, "notifShadeWindowController");
        Intrinsics.checkNotNullParameter(statusBarKeyguardViewManager2, "statusBarKeyguardViewManager");
        this.entry = notificationEntry;
        this.builder = builder2;
        this.postTime = j;
        this.handlers = list;
        this.actionIntent = pendingIntent;
        this.remoteInputs = remoteInputArr;
        this.freeformInput = remoteInput;
        this.expandedViewReplyButton = button;
        this.notificationRemoteInputManager = notificationRemoteInputManager2;
        this.shadeTransitionController = lockscreenShadeTransitionController;
        this.statusBar = statusBar2;
        this.statusBarStateController = sysuiStatusBarStateController;
        this.logger = notificationVoiceReplyLogger;
        this.notifShadeWindowController = notificationShadeWindowController;
        this.statusBarKeyguardViewManager = statusBarKeyguardViewManager2;
        String key = notificationEntry.getKey();
        Intrinsics.checkNotNullExpressionValue(key, "entry.key");
        this.notifKey = key;
        this.userId = notificationEntry.getSbn().getUser().getIdentifier();
    }

    public final NotificationEntry getEntry() {
        return this.entry;
    }

    public final Notification.Builder getBuilder() {
        return this.builder;
    }

    public final long getPostTime() {
        return this.postTime;
    }

    public final List<NotificationVoiceReplyHandler> getHandlers() {
        return this.handlers;
    }

    public final String getNotifKey() {
        return this.notifKey;
    }

    public final int getUserId() {
        return this.userId;
    }

    /* JADX WARNING: Removed duplicated region for block: B:101:0x023a A[Catch:{ all -> 0x0260 }] */
    /* JADX WARNING: Removed duplicated region for block: B:104:0x0253 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:105:0x0254  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00c0  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00d9 A[Catch:{ all -> 0x0265 }] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00db A[Catch:{ all -> 0x0265 }] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00e9 A[Catch:{ all -> 0x0265 }] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00eb A[Catch:{ all -> 0x0265 }] */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00f1 A[Catch:{ all -> 0x0265 }] */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x0101  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x0151 A[Catch:{ all -> 0x0159 }] */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x0172 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x0173  */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x0180 A[Catch:{ all -> 0x0263 }] */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x018e  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0035  */
    /* JADX WARNING: Removed duplicated region for block: B:95:0x020b A[Catch:{ all -> 0x0260 }] */
    /* JADX WARNING: Removed duplicated region for block: B:98:0x0224 A[Catch:{ all -> 0x0260 }, RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object focus(com.google.android.systemui.statusbar.notification.voicereplies.AuthStateRef r26, java.lang.String r27, kotlinx.coroutines.flow.MutableSharedFlow<kotlin.Pair<java.lang.String, com.android.systemui.statusbar.policy.RemoteInputView>> r28, kotlin.coroutines.Continuation<? super kotlin.Unit> r29) {
        /*
        // Method dump skipped, instructions count: 624
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget.focus(com.google.android.systemui.statusbar.notification.voicereplies.AuthStateRef, java.lang.String, kotlinx.coroutines.flow.MutableSharedFlow, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x003c  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x004b A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0054  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object awaitKeyguardNotOccluded(kotlin.coroutines.Continuation r6) {
        /*
        // Method dump skipped, instructions count: 120
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget.awaitKeyguardNotOccluded(kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ Object expandShade(Continuation continuation) {
        int state = this.statusBarStateController.getState();
        boolean z = false;
        if (state != 0) {
            if (state == 1) {
                CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(continuation), 1);
                LogBuffer logBuffer = this.logger.getLogBuffer();
                LogLevel logLevel = LogLevel.DEBUG;
                NotificationVoiceReplyLogger$logStatic$2 notificationVoiceReplyLogger$logStatic$2 = new NotificationVoiceReplyLogger$logStatic$2("On keyguard, waiting for SHADE_LOCKED state");
                if (!logBuffer.getFrozen()) {
                    logBuffer.push(logBuffer.obtain("NotifVoiceReply", logLevel, notificationVoiceReplyLogger$logStatic$2));
                }
                VoiceReplyTarget$expandShade$2$callback$1 voiceReplyTarget$expandShade$2$callback$1 = new VoiceReplyTarget$expandShade$2$callback$1(NotificationVoiceReplyManagerKt.AtomicLatch(), this, cancellableContinuationImpl);
                cancellableContinuationImpl.invokeOnCancellation(new VoiceReplyTarget$expandShade$2$1(this, voiceReplyTarget$expandShade$2$callback$1));
                this.statusBarStateController.addCallback(voiceReplyTarget$expandShade$2$callback$1);
                LockscreenShadeTransitionController.goToLockedShade$default(this.shadeTransitionController, getEntry().getRow(), false, 2, null);
                Object result = cancellableContinuationImpl.getResult();
                if (result == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                    DebugProbesKt.probeCoroutineSuspended(continuation);
                }
                return result;
            } else if (state != 2) {
                LogBuffer logBuffer2 = this.logger.getLogBuffer();
                LogLevel logLevel2 = LogLevel.DEBUG;
                NotificationVoiceReplyLogger$logStatic$2 notificationVoiceReplyLogger$logStatic$22 = new NotificationVoiceReplyLogger$logStatic$2("Unknown state, cancelling");
                if (!logBuffer2.getFrozen()) {
                    logBuffer2.push(logBuffer2.obtain("NotifVoiceReply", logLevel2, notificationVoiceReplyLogger$logStatic$22));
                }
                return Boxing.boxBoolean(z);
            }
        } else if (!this.statusBarStateController.isExpanded()) {
            CancellableContinuationImpl cancellableContinuationImpl2 = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(continuation), 1);
            LogBuffer logBuffer3 = this.logger.getLogBuffer();
            LogLevel logLevel3 = LogLevel.DEBUG;
            NotificationVoiceReplyLogger$logStatic$2 notificationVoiceReplyLogger$logStatic$23 = new NotificationVoiceReplyLogger$logStatic$2("Shade collapsed, waiting for expansion");
            if (!logBuffer3.getFrozen()) {
                logBuffer3.push(logBuffer3.obtain("NotifVoiceReply", logLevel3, notificationVoiceReplyLogger$logStatic$23));
            }
            VoiceReplyTarget$expandShade$3$callback$1 voiceReplyTarget$expandShade$3$callback$1 = new VoiceReplyTarget$expandShade$3$callback$1(NotificationVoiceReplyManagerKt.AtomicLatch(), this, cancellableContinuationImpl2);
            cancellableContinuationImpl2.invokeOnCancellation(new VoiceReplyTarget$expandShade$3$1(this, voiceReplyTarget$expandShade$3$callback$1));
            this.statusBarStateController.addCallback(voiceReplyTarget$expandShade$3$callback$1);
            this.statusBar.animateExpandNotificationsPanel();
            Object result2 = cancellableContinuationImpl2.getResult();
            if (result2 == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                DebugProbesKt.probeCoroutineSuspended(continuation);
            }
            return result2;
        }
        z = true;
        return Boxing.boxBoolean(z);
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ Object awaitFocusState(RemoteInputView remoteInputView, boolean z, Continuation continuation) {
        if (remoteInputView.editTextHasFocus() == z) {
            return Unit.INSTANCE;
        }
        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(continuation), 1);
        VoiceReplyTarget$awaitFocusState$2$listener$1 voiceReplyTarget$awaitFocusState$2$listener$1 = new VoiceReplyTarget$awaitFocusState$2$listener$1(z, NotificationVoiceReplyManagerKt.AtomicLatch(), remoteInputView, cancellableContinuationImpl);
        remoteInputView.addOnEditTextFocusChangedListener(voiceReplyTarget$awaitFocusState$2$listener$1);
        cancellableContinuationImpl.invokeOnCancellation(new VoiceReplyTarget$awaitFocusState$2$1(remoteInputView, voiceReplyTarget$awaitFocusState$2$listener$1));
        Object result = cancellableContinuationImpl.getResult();
        if (result == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return result == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? result : Unit.INSTANCE;
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ Object awaitFocusGained(RemoteInputView remoteInputView, Continuation continuation) {
        Object awaitFocusState = awaitFocusState(remoteInputView, true, continuation);
        return awaitFocusState == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? awaitFocusState : Unit.INSTANCE;
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ Object awaitFocusLost(RemoteInputView remoteInputView, Continuation continuation) {
        Object awaitFocusState = awaitFocusState(remoteInputView, false, continuation);
        return awaitFocusState == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? awaitFocusState : Unit.INSTANCE;
    }
}
