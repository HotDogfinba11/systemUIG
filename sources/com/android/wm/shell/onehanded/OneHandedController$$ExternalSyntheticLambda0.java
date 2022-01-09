package com.android.wm.shell.onehanded;

import android.window.WindowContainerTransaction;
import com.android.wm.shell.common.DisplayChangeController;

public final /* synthetic */ class OneHandedController$$ExternalSyntheticLambda0 implements DisplayChangeController.OnDisplayChangingListener {
    public final /* synthetic */ OneHandedController f$0;

    public /* synthetic */ OneHandedController$$ExternalSyntheticLambda0(OneHandedController oneHandedController) {
        this.f$0 = oneHandedController;
    }

    @Override // com.android.wm.shell.common.DisplayChangeController.OnDisplayChangingListener
    public final void onRotateDisplay(int i, int i2, int i3, WindowContainerTransaction windowContainerTransaction) {
        this.f$0.lambda$new$0(i, i2, i3, windowContainerTransaction);
    }
}
