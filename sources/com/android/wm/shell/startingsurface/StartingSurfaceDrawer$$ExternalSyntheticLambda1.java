package com.android.wm.shell.startingsurface;

import android.view.SurfaceControlViewHost;

public final /* synthetic */ class StartingSurfaceDrawer$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ SurfaceControlViewHost f$0;

    public /* synthetic */ StartingSurfaceDrawer$$ExternalSyntheticLambda1(SurfaceControlViewHost surfaceControlViewHost) {
        this.f$0 = surfaceControlViewHost;
    }

    public final void run() {
        this.f$0.release();
    }
}
