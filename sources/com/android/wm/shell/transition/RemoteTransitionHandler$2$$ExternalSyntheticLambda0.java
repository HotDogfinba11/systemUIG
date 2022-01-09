package com.android.wm.shell.transition;

import android.os.IBinder;
import android.window.WindowContainerTransaction;
import com.android.wm.shell.transition.RemoteTransitionHandler;
import com.android.wm.shell.transition.Transitions;

public final /* synthetic */ class RemoteTransitionHandler$2$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ RemoteTransitionHandler.AnonymousClass2 f$0;
    public final /* synthetic */ IBinder f$1;
    public final /* synthetic */ Transitions.TransitionFinishCallback f$2;
    public final /* synthetic */ WindowContainerTransaction f$3;

    public /* synthetic */ RemoteTransitionHandler$2$$ExternalSyntheticLambda0(RemoteTransitionHandler.AnonymousClass2 r1, IBinder iBinder, Transitions.TransitionFinishCallback transitionFinishCallback, WindowContainerTransaction windowContainerTransaction) {
        this.f$0 = r1;
        this.f$1 = iBinder;
        this.f$2 = transitionFinishCallback;
        this.f$3 = windowContainerTransaction;
    }

    public final void run() {
        RemoteTransitionHandler.AnonymousClass2.$r8$lambda$_iDEhAQxQKv3Us3C6J9F0tVbSiY(this.f$0, this.f$1, this.f$2, this.f$3);
    }
}
