package com.android.systemui.wmshell;

import com.android.systemui.model.SysUiState;
import com.android.wm.shell.pip.Pip;

public final /* synthetic */ class WMShell$$ExternalSyntheticLambda0 implements SysUiState.SysUiStateCallback {
    public final /* synthetic */ WMShell f$0;
    public final /* synthetic */ Pip f$1;

    public /* synthetic */ WMShell$$ExternalSyntheticLambda0(WMShell wMShell, Pip pip) {
        this.f$0 = wMShell;
        this.f$1 = pip;
    }

    @Override // com.android.systemui.model.SysUiState.SysUiStateCallback
    public final void onSystemUiStateChanged(int i) {
        this.f$0.lambda$initPip$0(this.f$1, i);
    }
}
