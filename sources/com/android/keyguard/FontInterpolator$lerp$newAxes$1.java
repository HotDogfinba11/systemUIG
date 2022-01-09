package com.android.keyguard;

import android.util.MathUtils;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: FontInterpolator.kt */
final class FontInterpolator$lerp$newAxes$1 extends Lambda implements Function3<String, Float, Float, Float> {
    final /* synthetic */ float $progress;
    final /* synthetic */ FontInterpolator this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    FontInterpolator$lerp$newAxes$1(FontInterpolator fontInterpolator, float f) {
        super(3);
        this.this$0 = fontInterpolator;
        this.$progress = f;
    }

    /* Return type fixed from 'java.lang.Object' to match base method */
    /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object, java.lang.Object, java.lang.Object] */
    @Override // kotlin.jvm.functions.Function3
    public /* bridge */ /* synthetic */ Float invoke(String str, Float f, Float f2) {
        return Float.valueOf(invoke(str, f, f2));
    }

    public final float invoke(String str, Float f, Float f2) {
        float f3;
        float f4;
        Intrinsics.checkNotNullParameter(str, "tag");
        if (Intrinsics.areEqual(str, "wght")) {
            FontInterpolator fontInterpolator = this.this$0;
            float f5 = 400.0f;
            if (f == null) {
                f4 = 400.0f;
            } else {
                f4 = f.floatValue();
            }
            if (f2 != null) {
                f5 = f2.floatValue();
            }
            return FontInterpolator.access$adjustWeight(fontInterpolator, MathUtils.lerp(f4, f5, this.$progress));
        } else if (Intrinsics.areEqual(str, "ital")) {
            FontInterpolator fontInterpolator2 = this.this$0;
            float f6 = 0.0f;
            if (f == null) {
                f3 = 0.0f;
            } else {
                f3 = f.floatValue();
            }
            if (f2 != null) {
                f6 = f2.floatValue();
            }
            return FontInterpolator.access$adjustItalic(fontInterpolator2, MathUtils.lerp(f3, f6, this.$progress));
        } else {
            if ((f == null || f2 == null) ? false : true) {
                return MathUtils.lerp(f.floatValue(), f2.floatValue(), this.$progress);
            }
            throw new IllegalArgumentException(Intrinsics.stringPlus("Unable to interpolate due to unknown default axes value : ", str).toString());
        }
    }
}
