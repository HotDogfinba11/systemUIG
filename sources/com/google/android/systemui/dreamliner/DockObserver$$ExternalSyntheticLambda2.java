package com.google.android.systemui.dreamliner;

import com.android.systemui.dock.DockManager;

public final /* synthetic */ class DockObserver$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ DockObserver f$0;
    public final /* synthetic */ DockManager.DockEventListener f$1;

    public /* synthetic */ DockObserver$$ExternalSyntheticLambda2(DockObserver dockObserver, DockManager.DockEventListener dockEventListener) {
        this.f$0 = dockObserver;
        this.f$1 = dockEventListener;
    }

    public final void run() {
        this.f$0.lambda$addListener$0(this.f$1);
    }
}
