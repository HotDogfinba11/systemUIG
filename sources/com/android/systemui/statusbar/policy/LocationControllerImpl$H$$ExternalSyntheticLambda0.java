package com.android.systemui.statusbar.policy;

import com.android.systemui.statusbar.policy.LocationController;
import com.android.systemui.statusbar.policy.LocationControllerImpl;
import java.util.function.Consumer;

public final /* synthetic */ class LocationControllerImpl$H$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ LocationControllerImpl.H f$0;

    public /* synthetic */ LocationControllerImpl$H$$ExternalSyntheticLambda0(LocationControllerImpl.H h) {
        this.f$0 = h;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.f$0.lambda$locationActiveChanged$0((LocationController.LocationChangeCallback) obj);
    }
}
