package com.android.wm.shell;

import android.os.Binder;

public final /* synthetic */ class TaskView$$ExternalSyntheticLambda9 implements Runnable {
    public final /* synthetic */ TaskView f$0;
    public final /* synthetic */ Binder f$1;

    public /* synthetic */ TaskView$$ExternalSyntheticLambda9(TaskView taskView, Binder binder) {
        this.f$0 = taskView;
        this.f$1 = binder;
    }

    public final void run() {
        this.f$0.lambda$prepareActivityOptions$0(this.f$1);
    }
}
