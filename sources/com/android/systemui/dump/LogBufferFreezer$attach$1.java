package com.android.systemui.dump;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* compiled from: LogBufferFreezer.kt */
public final class LogBufferFreezer$attach$1 extends BroadcastReceiver {
    final /* synthetic */ LogBufferFreezer this$0;

    LogBufferFreezer$attach$1(LogBufferFreezer logBufferFreezer) {
        this.this$0 = logBufferFreezer;
    }

    public void onReceive(Context context, Intent intent) {
        this.this$0.onBugreportStarted();
    }
}
