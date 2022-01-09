package com.android.wm.shell.startingsurface;

import android.os.IBinder;
import android.window.StartingWindowInfo;

public final /* synthetic */ class StartingWindowController$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ StartingWindowController f$0;
    public final /* synthetic */ StartingWindowInfo f$1;
    public final /* synthetic */ IBinder f$2;

    public /* synthetic */ StartingWindowController$$ExternalSyntheticLambda5(StartingWindowController startingWindowController, StartingWindowInfo startingWindowInfo, IBinder iBinder) {
        this.f$0 = startingWindowController;
        this.f$1 = startingWindowInfo;
        this.f$2 = iBinder;
    }

    public final void run() {
        this.f$0.lambda$addStartingWindow$0(this.f$1, this.f$2);
    }
}
