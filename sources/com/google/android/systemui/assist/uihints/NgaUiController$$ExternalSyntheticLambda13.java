package com.google.android.systemui.assist.uihints;

import android.view.animation.OvershootInterpolator;
import java.util.function.Function;

public final /* synthetic */ class NgaUiController$$ExternalSyntheticLambda13 implements Function {
    public final /* synthetic */ OvershootInterpolator f$0;

    public /* synthetic */ NgaUiController$$ExternalSyntheticLambda13(OvershootInterpolator overshootInterpolator) {
        this.f$0 = overshootInterpolator;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return NgaUiController.lambda$completeInvocation$5(this.f$0, (Float) obj);
    }
}
