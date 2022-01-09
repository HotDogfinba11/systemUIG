package com.google.android.systemui.columbus.actions;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* compiled from: LaunchApp.kt */
public final class LaunchApp$broadcastReceiver$1 extends BroadcastReceiver {
    final /* synthetic */ LaunchApp this$0;

    LaunchApp$broadcastReceiver$1(LaunchApp launchApp) {
        this.this$0 = launchApp;
    }

    public void onReceive(Context context, Intent intent) {
        this.this$0.updateAvailableAppsAndShortcutsAsync();
    }
}
