package com.android.wm.shell.common;

import com.android.wm.shell.common.DisplayImeController;

public final /* synthetic */ class DisplayImeController$PerDisplay$DisplayWindowInsetsControllerImpl$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ DisplayImeController.PerDisplay.DisplayWindowInsetsControllerImpl f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ boolean f$2;

    public /* synthetic */ DisplayImeController$PerDisplay$DisplayWindowInsetsControllerImpl$$ExternalSyntheticLambda0(DisplayImeController.PerDisplay.DisplayWindowInsetsControllerImpl displayWindowInsetsControllerImpl, int i, boolean z) {
        this.f$0 = displayWindowInsetsControllerImpl;
        this.f$1 = i;
        this.f$2 = z;
    }

    public final void run() {
        this.f$0.lambda$hideInsets$4(this.f$1, this.f$2);
    }
}
