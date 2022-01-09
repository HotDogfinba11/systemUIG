package com.google.android.systemui.dreamliner;

import android.os.ResultReceiver;

public final /* synthetic */ class DockObserver$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ DockObserver f$0;
    public final /* synthetic */ ResultReceiver f$1;

    public /* synthetic */ DockObserver$$ExternalSyntheticLambda1(DockObserver dockObserver, ResultReceiver resultReceiver) {
        this.f$0 = dockObserver;
        this.f$1 = resultReceiver;
    }

    public final void run() {
        this.f$0.lambda$configPhotoAction$3(this.f$1);
    }
}
