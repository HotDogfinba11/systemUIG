package com.android.wm.shell;

import android.graphics.Point;
import android.view.SurfaceControl;
import com.android.wm.shell.FullscreenTaskListener;
import com.android.wm.shell.common.SyncTransactionQueue;

public final /* synthetic */ class FullscreenTaskListener$$ExternalSyntheticLambda1 implements SyncTransactionQueue.TransactionRunnable {
    public final /* synthetic */ FullscreenTaskListener.TaskData f$0;
    public final /* synthetic */ Point f$1;

    public /* synthetic */ FullscreenTaskListener$$ExternalSyntheticLambda1(FullscreenTaskListener.TaskData taskData, Point point) {
        this.f$0 = taskData;
        this.f$1 = point;
    }

    @Override // com.android.wm.shell.common.SyncTransactionQueue.TransactionRunnable
    public final void runWithTransaction(SurfaceControl.Transaction transaction) {
        FullscreenTaskListener.lambda$onTaskInfoChanged$1(this.f$0, this.f$1, transaction);
    }
}
