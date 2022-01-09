package com.android.systemui.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.os.Looper;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import com.android.internal.util.Preconditions;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.logging.BroadcastDispatcherLogger;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt__SequencesKt;

/* compiled from: UserBroadcastDispatcher.kt */
public class UserBroadcastDispatcher implements Dumpable {
    public static final Companion Companion = new Companion(null);
    private static final AtomicInteger index = new AtomicInteger(0);
    private final ArrayMap<String, ActionReceiver> actionsToActionsReceivers = new ArrayMap<>();
    private final Executor bgExecutor;
    private final UserBroadcastDispatcher$bgHandler$1 bgHandler;
    private final Looper bgLooper;
    private final Context context;
    private final BroadcastDispatcherLogger logger;
    private final ArrayMap<BroadcastReceiver, Set<String>> receiverToActions = new ArrayMap<>();
    private final int userId;

    public static /* synthetic */ void getActionsToActionsReceivers$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
    }

    @Override // com.android.systemui.Dumpable
    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        Intrinsics.checkNotNullParameter(fileDescriptor, "fd");
        Intrinsics.checkNotNullParameter(printWriter, "pw");
        Intrinsics.checkNotNullParameter(strArr, "args");
        boolean z = printWriter instanceof IndentingPrintWriter;
        if (z) {
            ((IndentingPrintWriter) printWriter).increaseIndent();
        }
        for (Map.Entry<String, ActionReceiver> entry : getActionsToActionsReceivers$frameworks__base__packages__SystemUI__android_common__SystemUI_core().entrySet()) {
            printWriter.println(Intrinsics.stringPlus(entry.getKey(), ":"));
            entry.getValue().dump(fileDescriptor, printWriter, strArr);
        }
        if (z) {
            ((IndentingPrintWriter) printWriter).decreaseIndent();
        }
    }

    public UserBroadcastDispatcher(Context context2, int i, Looper looper, Executor executor, BroadcastDispatcherLogger broadcastDispatcherLogger) {
        Intrinsics.checkNotNullParameter(context2, "context");
        Intrinsics.checkNotNullParameter(looper, "bgLooper");
        Intrinsics.checkNotNullParameter(executor, "bgExecutor");
        Intrinsics.checkNotNullParameter(broadcastDispatcherLogger, "logger");
        this.context = context2;
        this.userId = i;
        this.bgLooper = looper;
        this.bgExecutor = executor;
        this.logger = broadcastDispatcherLogger;
        this.bgHandler = new UserBroadcastDispatcher$bgHandler$1(this, looper);
    }

    /* compiled from: UserBroadcastDispatcher.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final ArrayMap<String, ActionReceiver> getActionsToActionsReceivers$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        return this.actionsToActionsReceivers;
    }

    public final boolean isReceiverReferenceHeld$frameworks__base__packages__SystemUI__android_common__SystemUI_core(BroadcastReceiver broadcastReceiver) {
        boolean z;
        Intrinsics.checkNotNullParameter(broadcastReceiver, "receiver");
        Collection<ActionReceiver> values = this.actionsToActionsReceivers.values();
        Intrinsics.checkNotNullExpressionValue(values, "actionsToActionsReceivers.values");
        if (!values.isEmpty()) {
            Iterator<T> it = values.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (it.next().hasReceiver(broadcastReceiver)) {
                        z = true;
                        break;
                    }
                } else {
                    break;
                }
            }
            return z || this.receiverToActions.containsKey(broadcastReceiver);
        }
        z = false;
        if (z) {
            return true;
        }
    }

    public final void registerReceiver(ReceiverData receiverData) {
        Intrinsics.checkNotNullParameter(receiverData, "receiverData");
        this.bgHandler.obtainMessage(0, receiverData).sendToTarget();
    }

    public final void unregisterReceiver(BroadcastReceiver broadcastReceiver) {
        Intrinsics.checkNotNullParameter(broadcastReceiver, "receiver");
        this.bgHandler.obtainMessage(1, broadcastReceiver).sendToTarget();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private final void handleRegisterReceiver(ReceiverData receiverData) {
        Preconditions.checkState(this.bgHandler.getLooper().isCurrentThread(), "This method should only be called from BG thread");
        ArrayMap<BroadcastReceiver, Set<String>> arrayMap = this.receiverToActions;
        BroadcastReceiver receiver = receiverData.getReceiver();
        Set<String> set = arrayMap.get(receiver);
        if (set == null) {
            set = new ArraySet<>();
            arrayMap.put(receiver, set);
        }
        Set<String> set2 = set;
        Iterator<String> actionsIterator = receiverData.getFilter().actionsIterator();
        Sequence asSequence = actionsIterator == null ? null : SequencesKt__SequencesKt.asSequence(actionsIterator);
        if (asSequence == null) {
            asSequence = SequencesKt__SequencesKt.emptySequence();
        }
        boolean unused = CollectionsKt__MutableCollectionsKt.addAll(set2, asSequence);
        Iterator<String> actionsIterator2 = receiverData.getFilter().actionsIterator();
        Intrinsics.checkNotNullExpressionValue(actionsIterator2, "receiverData.filter.actionsIterator()");
        while (actionsIterator2.hasNext()) {
            String next = actionsIterator2.next();
            ArrayMap<String, ActionReceiver> actionsToActionsReceivers$frameworks__base__packages__SystemUI__android_common__SystemUI_core = getActionsToActionsReceivers$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
            ActionReceiver actionReceiver = actionsToActionsReceivers$frameworks__base__packages__SystemUI__android_common__SystemUI_core.get(next);
            if (actionReceiver == null) {
                Intrinsics.checkNotNullExpressionValue(next, "it");
                actionReceiver = createActionReceiver$frameworks__base__packages__SystemUI__android_common__SystemUI_core(next);
                actionsToActionsReceivers$frameworks__base__packages__SystemUI__android_common__SystemUI_core.put(next, actionReceiver);
            }
            actionReceiver.addReceiverData(receiverData);
        }
        this.logger.logReceiverRegistered(this.userId, receiverData.getReceiver());
    }

    public ActionReceiver createActionReceiver$frameworks__base__packages__SystemUI__android_common__SystemUI_core(String str) {
        Intrinsics.checkNotNullParameter(str, "action");
        return new ActionReceiver(str, this.userId, new UserBroadcastDispatcher$createActionReceiver$1(this), new UserBroadcastDispatcher$createActionReceiver$2(this, str), this.bgExecutor, this.logger);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private final void handleUnregisterReceiver(BroadcastReceiver broadcastReceiver) {
        Preconditions.checkState(this.bgHandler.getLooper().isCurrentThread(), "This method should only be called from BG thread");
        Set<String> orDefault = this.receiverToActions.getOrDefault(broadcastReceiver, new LinkedHashSet());
        Intrinsics.checkNotNullExpressionValue(orDefault, "receiverToActions.getOrDefault(receiver, mutableSetOf())");
        Iterator<T> it = orDefault.iterator();
        while (it.hasNext()) {
            ActionReceiver actionReceiver = getActionsToActionsReceivers$frameworks__base__packages__SystemUI__android_common__SystemUI_core().get(it.next());
            if (actionReceiver != null) {
                actionReceiver.removeReceiver(broadcastReceiver);
            }
        }
        this.receiverToActions.remove(broadcastReceiver);
        this.logger.logReceiverUnregistered(this.userId, broadcastReceiver);
    }
}
