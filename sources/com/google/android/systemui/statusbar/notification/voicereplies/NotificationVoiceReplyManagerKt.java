package com.google.android.systemui.statusbar.notification.voicereplies;

import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.people.Subscription;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

public final class NotificationVoiceReplyManagerKt {
    public static final Object awaitHunStateChange(HeadsUpManager headsUpManager, Continuation<? super Pair<NotificationEntry, Boolean>> continuation) {
        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(continuation), 1);
        NotificationVoiceReplyManagerKt$awaitHunStateChange$2$listener$1 notificationVoiceReplyManagerKt$awaitHunStateChange$2$listener$1 = new NotificationVoiceReplyManagerKt$awaitHunStateChange$2$listener$1(AtomicLatch(), headsUpManager, cancellableContinuationImpl);
        headsUpManager.addListener(notificationVoiceReplyManagerKt$awaitHunStateChange$2$listener$1);
        cancellableContinuationImpl.invokeOnCancellation(new NotificationVoiceReplyManagerKt$awaitHunStateChange$2$1(headsUpManager, notificationVoiceReplyManagerKt$awaitHunStateChange$2$listener$1));
        Object result = cancellableContinuationImpl.getResult();
        if (result == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return result;
    }

    public static final Object awaitStateChange(NotificationShadeWindowController notificationShadeWindowController, Continuation<? super StatusBarWindowState> continuation) {
        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(continuation), 1);
        NotificationVoiceReplyManagerKt$awaitStateChange$2$cb$1 notificationVoiceReplyManagerKt$awaitStateChange$2$cb$1 = new NotificationVoiceReplyManagerKt$awaitStateChange$2$cb$1(AtomicLatch(), notificationShadeWindowController, cancellableContinuationImpl);
        notificationShadeWindowController.registerCallback(notificationVoiceReplyManagerKt$awaitStateChange$2$cb$1);
        cancellableContinuationImpl.invokeOnCancellation(new NotificationVoiceReplyManagerKt$awaitStateChange$2$1(notificationShadeWindowController, notificationVoiceReplyManagerKt$awaitStateChange$2$cb$1));
        Object result = cancellableContinuationImpl.getResult();
        if (result == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return result;
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x004b  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x004d  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0055 A[EDGE_INSN: B:30:0x0055->B:24:0x0055 ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final android.widget.Button getReplyButton(android.view.View r4, android.app.RemoteInput r5) {
        /*
            r0 = 16908722(0x10201b2, float:2.3878445E-38)
            android.view.View r4 = r4.findViewById(r0)
            android.view.ViewGroup r4 = (android.view.ViewGroup) r4
            r0 = 0
            if (r4 != 0) goto L_0x000e
        L_0x000c:
            r4 = r0
            goto L_0x001b
        L_0x000e:
            kotlin.sequences.Sequence r4 = com.android.systemui.util.ConvenienceExtensionsKt.getChildren(r4)
            if (r4 != 0) goto L_0x0015
            goto L_0x000c
        L_0x0015:
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyManagerKt$getReplyButton$1 r1 = com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyManagerKt$getReplyButton$1.INSTANCE
            kotlin.sequences.Sequence r4 = kotlin.sequences.SequencesKt.filter(r4, r1)
        L_0x001b:
            if (r4 != 0) goto L_0x001e
            goto L_0x0061
        L_0x001e:
            java.util.Iterator r4 = r4.iterator()
        L_0x0022:
            boolean r1 = r4.hasNext()
            if (r1 == 0) goto L_0x0054
            java.lang.Object r1 = r4.next()
            r2 = r1
            android.view.View r2 = (android.view.View) r2
            r3 = 16909350(0x1020426, float:2.3880205E-38)
            java.lang.Object r2 = r2.getTag(r3)
            if (r2 != 0) goto L_0x003a
        L_0x0038:
            r2 = r0
            goto L_0x0049
        L_0x003a:
            boolean r3 = r2 instanceof java.lang.Object[]
            if (r3 == 0) goto L_0x0041
            java.lang.Object[] r2 = (java.lang.Object[]) r2
            goto L_0x0042
        L_0x0041:
            r2 = r0
        L_0x0042:
            if (r2 != 0) goto L_0x0045
            goto L_0x0038
        L_0x0045:
            kotlin.sequences.Sequence r2 = kotlin.collections.ArraysKt.asSequence(r2)
        L_0x0049:
            if (r2 != 0) goto L_0x004d
            r2 = 0
            goto L_0x0051
        L_0x004d:
            boolean r2 = kotlin.sequences.SequencesKt.contains(r2, r5)
        L_0x0051:
            if (r2 == 0) goto L_0x0022
            goto L_0x0055
        L_0x0054:
            r1 = r0
        L_0x0055:
            android.view.View r1 = (android.view.View) r1
            if (r1 != 0) goto L_0x005a
            goto L_0x0061
        L_0x005a:
            boolean r4 = r1 instanceof android.widget.Button
            if (r4 == 0) goto L_0x0061
            android.widget.Button r1 = (android.widget.Button) r1
            r0 = r1
        L_0x0061:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyManagerKt.getReplyButton(android.view.View, android.app.RemoteInput):android.widget.Button");
    }

    public static final Subscription Subscription(Function0<Unit> function0) {
        Intrinsics.checkNotNullParameter(function0, "block");
        return new SafeSubscription(function0);
    }

    public static final <K, V> V getOrPut(Map<K, V> map, K k, Function0<? extends V> function0) {
        V compute = map.compute(k, new NotificationVoiceReplyManagerKt$getOrPut$1(function0));
        Intrinsics.checkNotNull(compute);
        return compute;
    }

    public static final <T> Flow<T> distinctUntilChanged(Flow<? extends T> flow, Function2<? super T, ? super T, Boolean> function2) {
        Intrinsics.checkNotNullParameter(flow, "<this>");
        Intrinsics.checkNotNullParameter(function2, "areEqual");
        return FlowKt.flow(new NotificationVoiceReplyManagerKt$distinctUntilChanged$2(flow, function2, null));
    }

    public static final <T> Object collectLatest(Flow<? extends T> flow, Function2<? super T, ? super Continuation<? super Unit>, ? extends Object> function2, Continuation<? super Unit> continuation) {
        Object coroutineScope = CoroutineScopeKt.coroutineScope(new NotificationVoiceReplyManagerKt$collectLatest$2(flow, function2, null), continuation);
        return coroutineScope == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? coroutineScope : Unit.INSTANCE;
    }

    public static final AtomicBoolean AtomicLatch() {
        return new AtomicBoolean(true);
    }
}
