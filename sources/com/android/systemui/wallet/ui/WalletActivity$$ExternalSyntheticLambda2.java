package com.android.systemui.wallet.ui;

import com.android.systemui.plugins.ActivityStarter;

public final /* synthetic */ class WalletActivity$$ExternalSyntheticLambda2 implements ActivityStarter.OnDismissAction {
    public final /* synthetic */ WalletActivity f$0;

    public /* synthetic */ WalletActivity$$ExternalSyntheticLambda2(WalletActivity walletActivity) {
        this.f$0 = walletActivity;
    }

    @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
    public final boolean onDismiss() {
        return this.f$0.lambda$onCreate$0();
    }
}
