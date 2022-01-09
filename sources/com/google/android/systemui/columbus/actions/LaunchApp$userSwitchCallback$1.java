package com.google.android.systemui.columbus.actions;

import android.app.SynchronousUserSwitchObserver;
import android.os.RemoteException;

/* compiled from: LaunchApp.kt */
public final class LaunchApp$userSwitchCallback$1 extends SynchronousUserSwitchObserver {
    final /* synthetic */ LaunchApp this$0;

    LaunchApp$userSwitchCallback$1(LaunchApp launchApp) {
        this.this$0 = launchApp;
    }

    public void onUserSwitching(int i) throws RemoteException {
        this.this$0.updateAvailableAppsAndShortcutsAsync();
    }
}
