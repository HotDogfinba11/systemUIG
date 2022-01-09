package com.google.android.systemui.columbus.actions;

import android.app.SynchronousUserSwitchObserver;
import android.os.RemoteException;

/* compiled from: DeskClockAction.kt */
public final class DeskClockAction$userSwitchCallback$1 extends SynchronousUserSwitchObserver {
    final /* synthetic */ DeskClockAction this$0;

    DeskClockAction$userSwitchCallback$1(DeskClockAction deskClockAction) {
        this.this$0 = deskClockAction;
    }

    public void onUserSwitching(int i) throws RemoteException {
        this.this$0.updateBroadcastReceiver();
    }
}
