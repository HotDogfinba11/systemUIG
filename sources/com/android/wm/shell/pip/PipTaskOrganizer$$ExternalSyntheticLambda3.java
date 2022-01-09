package com.android.wm.shell.pip;

import android.view.SurfaceControl;
import com.android.wm.shell.common.SyncTransactionQueue;

public final /* synthetic */ class PipTaskOrganizer$$ExternalSyntheticLambda3 implements SyncTransactionQueue.TransactionRunnable {
    public final /* synthetic */ Runnable f$0;

    public /* synthetic */ PipTaskOrganizer$$ExternalSyntheticLambda3(Runnable runnable) {
        this.f$0 = runnable;
    }

    @Override // com.android.wm.shell.common.SyncTransactionQueue.TransactionRunnable
    public final void runWithTransaction(SurfaceControl.Transaction transaction) {
        this.f$0.run();
    }
}
