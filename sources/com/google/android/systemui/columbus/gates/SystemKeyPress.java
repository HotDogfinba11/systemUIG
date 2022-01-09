package com.google.android.systemui.columbus.gates;

import android.content.Context;
import android.os.Handler;
import com.android.systemui.statusbar.CommandQueue;
import java.util.Set;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SystemKeyPress.kt */
public final class SystemKeyPress extends TransientGate {
    private final Set<Integer> blockingKeys;
    private final CommandQueue commandQueue;
    private final SystemKeyPress$commandQueueCallbacks$1 commandQueueCallbacks = new SystemKeyPress$commandQueueCallbacks$1(this);
    private final long gateDuration;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public SystemKeyPress(Context context, Handler handler, CommandQueue commandQueue2, long j, Set<Integer> set) {
        super(context, handler);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(handler, "handler");
        Intrinsics.checkNotNullParameter(commandQueue2, "commandQueue");
        Intrinsics.checkNotNullParameter(set, "blockingKeys");
        this.commandQueue = commandQueue2;
        this.gateDuration = j;
        this.blockingKeys = set;
    }

    /* access modifiers changed from: protected */
    @Override // com.google.android.systemui.columbus.gates.Gate
    public void onActivate() {
        this.commandQueue.addCallback((CommandQueue.Callbacks) this.commandQueueCallbacks);
    }

    /* access modifiers changed from: protected */
    @Override // com.google.android.systemui.columbus.gates.Gate
    public void onDeactivate() {
        this.commandQueue.removeCallback((CommandQueue.Callbacks) this.commandQueueCallbacks);
    }
}
