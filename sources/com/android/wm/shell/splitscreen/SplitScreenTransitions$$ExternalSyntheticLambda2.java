package com.android.wm.shell.splitscreen;

import android.window.WindowContainerTransaction;
import android.window.WindowContainerTransactionCallback;
import com.android.wm.shell.transition.Transitions;

public final /* synthetic */ class SplitScreenTransitions$$ExternalSyntheticLambda2 implements Transitions.TransitionFinishCallback {
    public final /* synthetic */ SplitScreenTransitions f$0;

    public /* synthetic */ SplitScreenTransitions$$ExternalSyntheticLambda2(SplitScreenTransitions splitScreenTransitions) {
        this.f$0 = splitScreenTransitions;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionFinishCallback
    public final void onTransitionFinished(WindowContainerTransaction windowContainerTransaction, WindowContainerTransactionCallback windowContainerTransactionCallback) {
        SplitScreenTransitions.m611$r8$lambda$5r3nN0aRvuq8i_xsLICb1ldKQY(this.f$0, windowContainerTransaction, windowContainerTransactionCallback);
    }
}
