package com.android.systemui.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.logging.BroadcastDispatcherLogger;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt__SequencesKt;

public final class ActionReceiver extends BroadcastReceiver implements Dumpable {
    public static final Companion Companion = new Companion(null);
    private static final AtomicInteger index = new AtomicInteger(0);
    private final String action;
    private final ArraySet<String> activeCategories = new ArraySet<>();
    private final Executor bgExecutor;
    private final BroadcastDispatcherLogger logger;
    private final ArraySet<ReceiverData> receiverDatas = new ArraySet<>();
    private final Function2<BroadcastReceiver, IntentFilter, Unit> registerAction;
    private boolean registered;
    private final Function1<BroadcastReceiver, Unit> unregisterAction;
    private final int userId;

    @Override // com.android.systemui.Dumpable
    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        Intrinsics.checkNotNullParameter(fileDescriptor, "fd");
        Intrinsics.checkNotNullParameter(printWriter, "pw");
        Intrinsics.checkNotNullParameter(strArr, "args");
        if (printWriter instanceof IndentingPrintWriter) {
            ((IndentingPrintWriter) printWriter).increaseIndent();
        }
        printWriter.println(Intrinsics.stringPlus("Registered: ", Boolean.valueOf(getRegistered())));
        printWriter.println("Receivers:");
        boolean z = printWriter instanceof IndentingPrintWriter;
        if (z) {
            ((IndentingPrintWriter) printWriter).increaseIndent();
        }
        Iterator<T> it = this.receiverDatas.iterator();
        while (it.hasNext()) {
            printWriter.println(it.next().getReceiver());
        }
        if (z) {
            ((IndentingPrintWriter) printWriter).decreaseIndent();
        }
        printWriter.println(Intrinsics.stringPlus("Categories: ", CollectionsKt___CollectionsKt.joinToString$default(this.activeCategories, ", ", null, null, 0, null, null, 62, null)));
        if (z) {
            ((IndentingPrintWriter) printWriter).decreaseIndent();
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for r4v0, resolved type: kotlin.jvm.functions.Function2<? super android.content.BroadcastReceiver, ? super android.content.IntentFilter, kotlin.Unit> */
    /* JADX DEBUG: Multi-variable search result rejected for r5v0, resolved type: kotlin.jvm.functions.Function1<? super android.content.BroadcastReceiver, kotlin.Unit> */
    /* JADX WARN: Multi-variable type inference failed */
    public ActionReceiver(String str, int i, Function2<? super BroadcastReceiver, ? super IntentFilter, Unit> function2, Function1<? super BroadcastReceiver, Unit> function1, Executor executor, BroadcastDispatcherLogger broadcastDispatcherLogger) {
        Intrinsics.checkNotNullParameter(str, "action");
        Intrinsics.checkNotNullParameter(function2, "registerAction");
        Intrinsics.checkNotNullParameter(function1, "unregisterAction");
        Intrinsics.checkNotNullParameter(executor, "bgExecutor");
        Intrinsics.checkNotNullParameter(broadcastDispatcherLogger, "logger");
        this.action = str;
        this.userId = i;
        this.registerAction = function2;
        this.unregisterAction = function1;
        this.bgExecutor = executor;
        this.logger = broadcastDispatcherLogger;
    }

    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final AtomicInteger getIndex() {
            return ActionReceiver.index;
        }
    }

    public final boolean getRegistered() {
        return this.registered;
    }

    public final void addReceiverData(ReceiverData receiverData) {
        Intrinsics.checkNotNullParameter(receiverData, "receiverData");
        if (receiverData.getFilter().hasAction(this.action)) {
            ArraySet<String> arraySet = this.activeCategories;
            Iterator<String> categoriesIterator = receiverData.getFilter().categoriesIterator();
            Sequence asSequence = categoriesIterator == null ? null : SequencesKt__SequencesKt.asSequence(categoriesIterator);
            if (asSequence == null) {
                asSequence = SequencesKt__SequencesKt.emptySequence();
            }
            boolean z = CollectionsKt__MutableCollectionsKt.addAll(arraySet, asSequence);
            if (this.receiverDatas.add(receiverData) && this.receiverDatas.size() == 1) {
                this.registerAction.invoke(this, createFilter());
                this.registered = true;
            } else if (z) {
                this.unregisterAction.invoke(this);
                this.registerAction.invoke(this, createFilter());
            }
        } else {
            throw new IllegalArgumentException("Trying to attach to " + this.action + " without correct action,receiver: " + receiverData.getReceiver());
        }
    }

    public final boolean hasReceiver(BroadcastReceiver broadcastReceiver) {
        Intrinsics.checkNotNullParameter(broadcastReceiver, "receiver");
        ArraySet<ReceiverData> arraySet = this.receiverDatas;
        if ((arraySet instanceof Collection) && arraySet.isEmpty()) {
            return false;
        }
        Iterator<T> it = arraySet.iterator();
        while (it.hasNext()) {
            if (Intrinsics.areEqual(it.next().getReceiver(), broadcastReceiver)) {
                return true;
            }
        }
        return false;
    }

    private final IntentFilter createFilter() {
        IntentFilter intentFilter = new IntentFilter(this.action);
        Iterator<T> it = this.activeCategories.iterator();
        while (it.hasNext()) {
            intentFilter.addCategory(it.next());
        }
        return intentFilter;
    }

    public final void removeReceiver(BroadcastReceiver broadcastReceiver) {
        Intrinsics.checkNotNullParameter(broadcastReceiver, "receiver");
        if ((CollectionsKt__MutableCollectionsKt.removeAll(this.receiverDatas, new ActionReceiver$removeReceiver$1(broadcastReceiver))) && this.receiverDatas.isEmpty() && this.registered) {
            this.unregisterAction.invoke(this);
            this.registered = false;
            this.activeCategories.clear();
        }
    }

    public void onReceive(Context context, Intent intent) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(intent, "intent");
        if (Intrinsics.areEqual(intent.getAction(), this.action)) {
            int andIncrement = Companion.getIndex().getAndIncrement();
            this.logger.logBroadcastReceived(andIncrement, this.userId, intent);
            this.bgExecutor.execute(new ActionReceiver$onReceive$1(this, intent, context, andIncrement));
            return;
        }
        throw new IllegalStateException("Received intent for " + ((Object) intent.getAction()) + " in receiver for " + this.action + '}');
    }
}
