package com.android.wm.shell.transition;

import android.os.IBinder;
import com.android.wm.shell.transition.Transitions;

public final /* synthetic */ class RemoteTransitionHandler$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ RemoteTransitionHandler f$0;
    public final /* synthetic */ IBinder f$1;
    public final /* synthetic */ Transitions.TransitionFinishCallback f$2;

    public /* synthetic */ RemoteTransitionHandler$$ExternalSyntheticLambda1(RemoteTransitionHandler remoteTransitionHandler, IBinder iBinder, Transitions.TransitionFinishCallback transitionFinishCallback) {
        this.f$0 = remoteTransitionHandler;
        this.f$1 = iBinder;
        this.f$2 = transitionFinishCallback;
    }

    public final void run() {
        RemoteTransitionHandler.$r8$lambda$IS2yUP_MzeyhoI_OxNFt2_XyI6U(this.f$0, this.f$1, this.f$2);
    }
}
