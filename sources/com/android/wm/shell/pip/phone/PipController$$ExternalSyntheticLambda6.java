package com.android.wm.shell.pip.phone;

import com.android.wm.shell.common.DisplayLayout;

public final /* synthetic */ class PipController$$ExternalSyntheticLambda6 implements Runnable {
    public final /* synthetic */ PipController f$0;
    public final /* synthetic */ DisplayLayout f$1;

    public /* synthetic */ PipController$$ExternalSyntheticLambda6(PipController pipController, DisplayLayout displayLayout) {
        this.f$0 = pipController;
        this.f$1 = displayLayout;
    }

    public final void run() {
        this.f$0.lambda$onDisplayChanged$5(this.f$1);
    }
}
