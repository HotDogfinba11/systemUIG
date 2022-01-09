package com.android.wm.shell.pip.phone;

import android.view.Choreographer;

public final /* synthetic */ class PipMotionHelper$1$$ExternalSyntheticLambda0 implements Choreographer.FrameCallback {
    public final /* synthetic */ Runnable f$0;

    public /* synthetic */ PipMotionHelper$1$$ExternalSyntheticLambda0(Runnable runnable) {
        this.f$0 = runnable;
    }

    public final void doFrame(long j) {
        this.f$0.run();
    }
}
