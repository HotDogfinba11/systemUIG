package com.android.wm.shell.transition;

import android.os.IBinder;
import com.android.wm.shell.transition.Transitions;
import java.util.ArrayList;

public final /* synthetic */ class DefaultTransitionHandler$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ DefaultTransitionHandler f$0;
    public final /* synthetic */ ArrayList f$1;
    public final /* synthetic */ IBinder f$2;
    public final /* synthetic */ Transitions.TransitionFinishCallback f$3;

    public /* synthetic */ DefaultTransitionHandler$$ExternalSyntheticLambda2(DefaultTransitionHandler defaultTransitionHandler, ArrayList arrayList, IBinder iBinder, Transitions.TransitionFinishCallback transitionFinishCallback) {
        this.f$0 = defaultTransitionHandler;
        this.f$1 = arrayList;
        this.f$2 = iBinder;
        this.f$3 = transitionFinishCallback;
    }

    public final void run() {
        DefaultTransitionHandler.m630$r8$lambda$42XeKNKaf8yBeFAl_GmLtqm558(this.f$0, this.f$1, this.f$2, this.f$3);
    }
}
