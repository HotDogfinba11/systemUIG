package com.android.wm.shell.transition;

import android.window.WindowContainerTransaction;
import com.android.wm.shell.transition.Transitions;

public final /* synthetic */ class OneShotRemoteHandler$1$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ Transitions.TransitionFinishCallback f$0;
    public final /* synthetic */ WindowContainerTransaction f$1;

    public /* synthetic */ OneShotRemoteHandler$1$$ExternalSyntheticLambda0(Transitions.TransitionFinishCallback transitionFinishCallback, WindowContainerTransaction windowContainerTransaction) {
        this.f$0 = transitionFinishCallback;
        this.f$1 = windowContainerTransaction;
    }

    public final void run() {
        this.f$0.onTransitionFinished(this.f$1, null);
    }
}
