package com.android.wm.shell.transition;

import android.window.WindowContainerTransaction;
import android.window.WindowContainerTransactionCallback;
import com.android.wm.shell.transition.Transitions;

public final /* synthetic */ class Transitions$$ExternalSyntheticLambda1 implements Transitions.TransitionFinishCallback {
    public final /* synthetic */ Transitions f$0;
    public final /* synthetic */ Transitions.ActiveTransition f$1;

    public /* synthetic */ Transitions$$ExternalSyntheticLambda1(Transitions transitions, Transitions.ActiveTransition activeTransition) {
        this.f$0 = transitions;
        this.f$1 = activeTransition;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionFinishCallback
    public final void onTransitionFinished(WindowContainerTransaction windowContainerTransaction, WindowContainerTransactionCallback windowContainerTransactionCallback) {
        this.f$0.lambda$startAnimation$2(this.f$1, windowContainerTransaction, windowContainerTransactionCallback);
    }
}
