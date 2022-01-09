package com.android.systemui.statusbar.policy;

import com.android.systemui.statusbar.policy.ZenModeController;
import java.util.function.Consumer;

public final /* synthetic */ class ZenModeControllerImpl$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int f$0;

    public /* synthetic */ ZenModeControllerImpl$$ExternalSyntheticLambda0(int i) {
        this.f$0 = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ZenModeControllerImpl.lambda$fireZenChanged$2(this.f$0, (ZenModeController.Callback) obj);
    }
}
