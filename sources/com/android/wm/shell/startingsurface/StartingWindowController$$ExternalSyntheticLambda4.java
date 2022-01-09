package com.android.wm.shell.startingsurface;

import android.graphics.Rect;
import android.view.SurfaceControl;

public final /* synthetic */ class StartingWindowController$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ StartingWindowController f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ SurfaceControl f$2;
    public final /* synthetic */ Rect f$3;
    public final /* synthetic */ boolean f$4;

    public /* synthetic */ StartingWindowController$$ExternalSyntheticLambda4(StartingWindowController startingWindowController, int i, SurfaceControl surfaceControl, Rect rect, boolean z) {
        this.f$0 = startingWindowController;
        this.f$1 = i;
        this.f$2 = surfaceControl;
        this.f$3 = rect;
        this.f$4 = z;
    }

    public final void run() {
        this.f$0.lambda$removeStartingWindow$4(this.f$1, this.f$2, this.f$3, this.f$4);
    }
}
