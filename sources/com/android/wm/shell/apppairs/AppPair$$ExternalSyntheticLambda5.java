package com.android.wm.shell.apppairs;

import android.graphics.Rect;
import android.view.SurfaceControl;
import com.android.wm.shell.common.SyncTransactionQueue;

public final /* synthetic */ class AppPair$$ExternalSyntheticLambda5 implements SyncTransactionQueue.TransactionRunnable {
    public final /* synthetic */ AppPair f$0;
    public final /* synthetic */ SurfaceControl f$1;
    public final /* synthetic */ Rect f$2;

    public /* synthetic */ AppPair$$ExternalSyntheticLambda5(AppPair appPair, SurfaceControl surfaceControl, Rect rect) {
        this.f$0 = appPair;
        this.f$1 = surfaceControl;
        this.f$2 = rect;
    }

    @Override // com.android.wm.shell.common.SyncTransactionQueue.TransactionRunnable
    public final void runWithTransaction(SurfaceControl.Transaction transaction) {
        AppPair.m487$r8$lambda$XCCPRpQoOIRxiNz0IBuvyLkmj4(this.f$0, this.f$1, this.f$2, transaction);
    }
}
