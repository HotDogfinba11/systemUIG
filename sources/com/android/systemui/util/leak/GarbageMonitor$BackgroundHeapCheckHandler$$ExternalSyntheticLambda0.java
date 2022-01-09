package com.android.systemui.util.leak;

public final /* synthetic */ class GarbageMonitor$BackgroundHeapCheckHandler$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ GarbageMonitor f$0;

    public /* synthetic */ GarbageMonitor$BackgroundHeapCheckHandler$$ExternalSyntheticLambda0(GarbageMonitor garbageMonitor) {
        this.f$0 = garbageMonitor;
    }

    public final void run() {
        this.f$0.reinspectGarbageAfterGc();
    }
}
