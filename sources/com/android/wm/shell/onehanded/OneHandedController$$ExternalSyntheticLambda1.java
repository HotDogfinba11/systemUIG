package com.android.wm.shell.onehanded;

import com.android.wm.shell.onehanded.OneHandedTimeoutHandler;

public final /* synthetic */ class OneHandedController$$ExternalSyntheticLambda1 implements OneHandedTimeoutHandler.TimeoutListener {
    public final /* synthetic */ OneHandedController f$0;

    public /* synthetic */ OneHandedController$$ExternalSyntheticLambda1(OneHandedController oneHandedController) {
        this.f$0 = oneHandedController;
    }

    @Override // com.android.wm.shell.onehanded.OneHandedTimeoutHandler.TimeoutListener
    public final void onTimeout(int i) {
        this.f$0.lambda$setupTimeoutListener$3(i);
    }
}
