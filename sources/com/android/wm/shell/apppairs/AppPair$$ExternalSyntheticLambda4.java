package com.android.wm.shell.apppairs;

import android.app.ActivityManager;
import android.view.SurfaceControl;
import com.android.wm.shell.common.SyncTransactionQueue;

public final /* synthetic */ class AppPair$$ExternalSyntheticLambda4 implements SyncTransactionQueue.TransactionRunnable {
    public final /* synthetic */ AppPair f$0;
    public final /* synthetic */ ActivityManager.RunningTaskInfo f$1;

    public /* synthetic */ AppPair$$ExternalSyntheticLambda4(AppPair appPair, ActivityManager.RunningTaskInfo runningTaskInfo) {
        this.f$0 = appPair;
        this.f$1 = runningTaskInfo;
    }

    @Override // com.android.wm.shell.common.SyncTransactionQueue.TransactionRunnable
    public final void runWithTransaction(SurfaceControl.Transaction transaction) {
        AppPair.m488$r8$lambda$gloreNxsivFR9skj97RqcZeuOo(this.f$0, this.f$1, transaction);
    }
}
