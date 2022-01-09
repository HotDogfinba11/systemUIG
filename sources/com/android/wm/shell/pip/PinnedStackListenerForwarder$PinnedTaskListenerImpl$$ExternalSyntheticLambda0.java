package com.android.wm.shell.pip;

import com.android.wm.shell.pip.PinnedStackListenerForwarder;

public final /* synthetic */ class PinnedStackListenerForwarder$PinnedTaskListenerImpl$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ PinnedStackListenerForwarder.PinnedTaskListenerImpl f$0;
    public final /* synthetic */ float f$1;

    public /* synthetic */ PinnedStackListenerForwarder$PinnedTaskListenerImpl$$ExternalSyntheticLambda0(PinnedStackListenerForwarder.PinnedTaskListenerImpl pinnedTaskListenerImpl, float f) {
        this.f$0 = pinnedTaskListenerImpl;
        this.f$1 = f;
    }

    public final void run() {
        this.f$0.lambda$onAspectRatioChanged$4(this.f$1);
    }
}
