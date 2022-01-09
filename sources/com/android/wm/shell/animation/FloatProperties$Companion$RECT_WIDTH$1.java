package com.android.wm.shell.animation;

import android.graphics.Rect;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: FloatProperties.kt */
public final class FloatProperties$Companion$RECT_WIDTH$1 extends FloatPropertyCompat<Rect> {
    FloatProperties$Companion$RECT_WIDTH$1() {
        super("RectWidth");
    }

    public float getValue(Rect rect) {
        Intrinsics.checkNotNullParameter(rect, "rect");
        return (float) rect.width();
    }

    public void setValue(Rect rect, float f) {
        Intrinsics.checkNotNullParameter(rect, "rect");
        rect.right = rect.left + ((int) f);
    }
}
