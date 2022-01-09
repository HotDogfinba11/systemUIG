package com.android.wm.shell.startingsurface;

import android.content.res.TypedArray;
import java.util.function.UnaryOperator;

public final /* synthetic */ class SplashscreenContentDrawer$$ExternalSyntheticLambda8 implements UnaryOperator {
    public final /* synthetic */ TypedArray f$0;

    public /* synthetic */ SplashscreenContentDrawer$$ExternalSyntheticLambda8(TypedArray typedArray) {
        this.f$0 = typedArray;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return SplashscreenContentDrawer.lambda$peekLegacySplashscreenContent$1(this.f$0, (Integer) obj);
    }
}
