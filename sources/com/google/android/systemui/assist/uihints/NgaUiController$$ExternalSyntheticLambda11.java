package com.google.android.systemui.assist.uihints;

import android.graphics.Region;
import java.util.function.Consumer;

public final /* synthetic */ class NgaUiController$$ExternalSyntheticLambda11 implements Consumer {
    public final /* synthetic */ Region f$0;

    public /* synthetic */ NgaUiController$$ExternalSyntheticLambda11(Region region) {
        this.f$0 = region;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        NgaUiController.lambda$onComputeInternalInsets$10(this.f$0, (Region) obj);
    }
}
