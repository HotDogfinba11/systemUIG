package com.android.systemui.statusbar.phone;

import com.android.systemui.controls.management.ControlsListingController;
import java.util.function.Consumer;

public final /* synthetic */ class KeyguardBottomAreaView$$ExternalSyntheticLambda4 implements Consumer {
    public final /* synthetic */ KeyguardBottomAreaView f$0;

    public /* synthetic */ KeyguardBottomAreaView$$ExternalSyntheticLambda4(KeyguardBottomAreaView keyguardBottomAreaView) {
        this.f$0 = keyguardBottomAreaView;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.f$0.lambda$onDetachedFromWindow$6((ControlsListingController) obj);
    }
}
