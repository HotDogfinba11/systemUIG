package com.google.android.systemui.assist;

import android.content.BroadcastReceiver;

public final /* synthetic */ class OpaEnabledReceiver$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ BroadcastReceiver.PendingResult f$0;

    public /* synthetic */ OpaEnabledReceiver$$ExternalSyntheticLambda0(BroadcastReceiver.PendingResult pendingResult) {
        this.f$0 = pendingResult;
    }

    public final void run() {
        this.f$0.finish();
    }
}
