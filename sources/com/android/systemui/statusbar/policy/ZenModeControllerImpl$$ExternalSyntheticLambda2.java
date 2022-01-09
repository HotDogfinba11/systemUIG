package com.android.systemui.statusbar.policy;

import android.service.notification.ZenModeConfig;
import com.android.systemui.statusbar.policy.ZenModeController;
import java.util.function.Consumer;

public final /* synthetic */ class ZenModeControllerImpl$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ ZenModeConfig.ZenRule f$0;

    public /* synthetic */ ZenModeControllerImpl$$ExternalSyntheticLambda2(ZenModeConfig.ZenRule zenRule) {
        this.f$0 = zenRule;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ZenModeControllerImpl.lambda$fireManualRuleChanged$4(this.f$0, (ZenModeController.Callback) obj);
    }
}
