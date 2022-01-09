package com.android.wm.shell.pip.phone;

import android.window.WindowContainerTransaction;
import com.android.wm.shell.common.DisplayChangeController;

public final /* synthetic */ class PipController$$ExternalSyntheticLambda1 implements DisplayChangeController.OnDisplayChangingListener {
    public final /* synthetic */ PipController f$0;

    public /* synthetic */ PipController$$ExternalSyntheticLambda1(PipController pipController) {
        this.f$0 = pipController;
    }

    @Override // com.android.wm.shell.common.DisplayChangeController.OnDisplayChangingListener
    public final void onRotateDisplay(int i, int i2, int i3, WindowContainerTransaction windowContainerTransaction) {
        this.f$0.lambda$new$0(i, i2, i3, windowContainerTransaction);
    }
}
