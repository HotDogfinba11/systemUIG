package com.google.android.systemui.assist.uihints;

import android.graphics.Region;
import java.util.function.Consumer;

public final /* synthetic */ class NgaUiController$$ExternalSyntheticLambda12 implements Consumer {
    public final /* synthetic */ Region f$0;

    public /* synthetic */ NgaUiController$$ExternalSyntheticLambda12(Region region) {
        this.f$0 = region;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        NgaUiController.lambda$onComputeInternalInsets$8(this.f$0, (Region) obj);
    }
}
