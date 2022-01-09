package com.google.android.systemui.gamedashboard;

import android.content.Intent;
import com.android.systemui.plugins.ActivityStarter;

public final /* synthetic */ class ScreenRecordController$$ExternalSyntheticLambda0 implements ActivityStarter.OnDismissAction {
    public final /* synthetic */ ScreenRecordController f$0;
    public final /* synthetic */ Intent f$1;

    public /* synthetic */ ScreenRecordController$$ExternalSyntheticLambda0(ScreenRecordController screenRecordController, Intent intent) {
        this.f$0 = screenRecordController;
        this.f$1 = intent;
    }

    @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
    public final boolean onDismiss() {
        return this.f$0.lambda$showPrompt$2(this.f$1);
    }
}
