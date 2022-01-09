package com.android.wm.shell.startingsurface;

import android.content.res.TypedArray;
import java.util.function.UnaryOperator;

public final /* synthetic */ class SplashscreenContentDrawer$$ExternalSyntheticLambda6 implements UnaryOperator {
    public final /* synthetic */ TypedArray f$0;

    public /* synthetic */ SplashscreenContentDrawer$$ExternalSyntheticLambda6(TypedArray typedArray) {
        this.f$0 = typedArray;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return SplashscreenContentDrawer.lambda$getWindowAttrs$4(this.f$0, (Integer) obj);
    }
}
