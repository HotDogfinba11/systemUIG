package com.google.android.systemui.assist;

import android.content.BroadcastReceiver;

public final /* synthetic */ class OpaEnabledReceiver$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ OpaEnabledReceiver f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ BroadcastReceiver.PendingResult f$2;

    public /* synthetic */ OpaEnabledReceiver$$ExternalSyntheticLambda2(OpaEnabledReceiver opaEnabledReceiver, boolean z, BroadcastReceiver.PendingResult pendingResult) {
        this.f$0 = opaEnabledReceiver;
        this.f$1 = z;
        this.f$2 = pendingResult;
    }

    public final void run() {
        this.f$0.lambda$updateOpaEnabledState$2(this.f$1, this.f$2);
    }
}
