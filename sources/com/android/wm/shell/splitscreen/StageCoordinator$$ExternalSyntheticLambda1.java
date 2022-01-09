package com.android.wm.shell.splitscreen;

import android.view.SurfaceControl;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.common.split.SplitLayout;

public final /* synthetic */ class StageCoordinator$$ExternalSyntheticLambda1 implements SyncTransactionQueue.TransactionRunnable {
    public final /* synthetic */ SplitLayout f$0;
    public final /* synthetic */ StageTaskListener f$1;
    public final /* synthetic */ StageTaskListener f$2;

    public /* synthetic */ StageCoordinator$$ExternalSyntheticLambda1(SplitLayout splitLayout, StageTaskListener stageTaskListener, StageTaskListener stageTaskListener2) {
        this.f$0 = splitLayout;
        this.f$1 = stageTaskListener;
        this.f$2 = stageTaskListener2;
    }

    @Override // com.android.wm.shell.common.SyncTransactionQueue.TransactionRunnable
    public final void runWithTransaction(SurfaceControl.Transaction transaction) {
        StageCoordinator.lambda$onBoundsChanging$2(this.f$0, this.f$1, this.f$2, transaction);
    }
}
