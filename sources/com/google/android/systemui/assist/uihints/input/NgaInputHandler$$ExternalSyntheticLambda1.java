package com.google.android.systemui.assist.uihints.input;

import android.graphics.Region;
import java.util.function.Consumer;

public final /* synthetic */ class NgaInputHandler$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ Region f$0;

    public /* synthetic */ NgaInputHandler$$ExternalSyntheticLambda1(Region region) {
        this.f$0 = region;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        NgaInputHandler.lambda$handleMotionEvent$1(this.f$0, (Region) obj);
    }
}
