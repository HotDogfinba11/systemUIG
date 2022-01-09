package com.android.systemui.dump;

import android.content.IntentFilter;
import android.os.UserHandle;
import android.util.Log;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.util.concurrent.TimeUnit;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: LogBufferFreezer.kt */
public final class LogBufferFreezer {
    private final DumpManager dumpManager;
    private final DelayableExecutor executor;
    private final long freezeDuration;
    private Runnable pendingToken;

    public LogBufferFreezer(DumpManager dumpManager2, DelayableExecutor delayableExecutor, long j) {
        Intrinsics.checkNotNullParameter(dumpManager2, "dumpManager");
        Intrinsics.checkNotNullParameter(delayableExecutor, "executor");
        this.dumpManager = dumpManager2;
        this.executor = delayableExecutor;
        this.freezeDuration = j;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public LogBufferFreezer(DumpManager dumpManager2, DelayableExecutor delayableExecutor) {
        this(dumpManager2, delayableExecutor, TimeUnit.MINUTES.toMillis(5));
        Intrinsics.checkNotNullParameter(dumpManager2, "dumpManager");
        Intrinsics.checkNotNullParameter(delayableExecutor, "executor");
    }

    public final void attach(BroadcastDispatcher broadcastDispatcher) {
        Intrinsics.checkNotNullParameter(broadcastDispatcher, "broadcastDispatcher");
        broadcastDispatcher.registerReceiver(new LogBufferFreezer$attach$1(this), new IntentFilter("com.android.internal.intent.action.BUGREPORT_STARTED"), this.executor, UserHandle.ALL);
    }

    /* access modifiers changed from: private */
    public final void onBugreportStarted() {
        Runnable runnable = this.pendingToken;
        if (runnable != null) {
            runnable.run();
        }
        Log.i("LogBufferFreezer", "Freezing log buffers");
        this.dumpManager.freezeBuffers();
        this.pendingToken = this.executor.executeDelayed(new LogBufferFreezer$onBugreportStarted$1(this), this.freezeDuration);
    }
}
