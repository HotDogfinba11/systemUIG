package com.android.wm.shell.startingsurface;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import java.util.function.UnaryOperator;

public final /* synthetic */ class SplashscreenContentDrawer$$ExternalSyntheticLambda4 implements UnaryOperator {
    public final /* synthetic */ TypedArray f$0;

    public /* synthetic */ SplashscreenContentDrawer$$ExternalSyntheticLambda4(TypedArray typedArray) {
        this.f$0 = typedArray;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        Drawable drawable = (Drawable) obj;
        return this.f$0.getDrawable(59);
    }
}
