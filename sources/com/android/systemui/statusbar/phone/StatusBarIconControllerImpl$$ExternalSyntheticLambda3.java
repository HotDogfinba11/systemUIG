package com.android.systemui.statusbar.phone;

import com.android.systemui.statusbar.phone.StatusBarIconController;
import java.util.function.Consumer;

public final /* synthetic */ class StatusBarIconControllerImpl$$ExternalSyntheticLambda3 implements Consumer {
    public final /* synthetic */ int f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ StatusBarIconControllerImpl$$ExternalSyntheticLambda3(int i, int i2) {
        this.f$0 = i;
        this.f$1 = i2;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        StatusBarIconControllerImpl.lambda$setIconAccessibilityLiveRegion$2(this.f$0, this.f$1, (StatusBarIconController.IconManager) obj);
    }
}
