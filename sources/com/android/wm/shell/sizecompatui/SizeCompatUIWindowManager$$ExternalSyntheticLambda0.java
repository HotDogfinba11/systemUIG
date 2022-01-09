package com.android.wm.shell.sizecompatui;

import android.view.SurfaceControl;
import com.android.wm.shell.common.SyncTransactionQueue;

public final /* synthetic */ class SizeCompatUIWindowManager$$ExternalSyntheticLambda0 implements SyncTransactionQueue.TransactionRunnable {
    public final /* synthetic */ SurfaceControl f$0;

    public /* synthetic */ SizeCompatUIWindowManager$$ExternalSyntheticLambda0(SurfaceControl surfaceControl) {
        this.f$0 = surfaceControl;
    }

    @Override // com.android.wm.shell.common.SyncTransactionQueue.TransactionRunnable
    public final void runWithTransaction(SurfaceControl.Transaction transaction) {
        transaction.remove(this.f$0);
    }
}
