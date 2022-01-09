package com.android.systemui.statusbar.policy;

import android.service.notification.ZenModeConfig;
import com.android.systemui.statusbar.policy.ZenModeController;
import java.util.function.Consumer;

public final /* synthetic */ class ZenModeControllerImpl$$ExternalSyntheticLambda3 implements Consumer {
    public final /* synthetic */ ZenModeConfig f$0;

    public /* synthetic */ ZenModeControllerImpl$$ExternalSyntheticLambda3(ZenModeConfig zenModeConfig) {
        this.f$0 = zenModeConfig;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ZenModeControllerImpl.lambda$fireConfigChanged$6(this.f$0, (ZenModeController.Callback) obj);
    }
}
