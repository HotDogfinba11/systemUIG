package com.android.systemui.statusbar.policy;

import com.android.systemui.statusbar.policy.ZenModeController;
import java.util.function.Consumer;

public final /* synthetic */ class ZenModeControllerImpl$$ExternalSyntheticLambda5 implements Consumer {
    public static final /* synthetic */ ZenModeControllerImpl$$ExternalSyntheticLambda5 INSTANCE = new ZenModeControllerImpl$$ExternalSyntheticLambda5();

    private /* synthetic */ ZenModeControllerImpl$$ExternalSyntheticLambda5() {
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ZenModeControllerImpl.lambda$fireEffectsSuppressorChanged$1((ZenModeController.Callback) obj);
    }
}
