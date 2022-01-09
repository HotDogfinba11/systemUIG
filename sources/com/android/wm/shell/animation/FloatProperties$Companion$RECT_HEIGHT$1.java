package com.android.wm.shell.animation;

import android.graphics.Rect;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: FloatProperties.kt */
public final class FloatProperties$Companion$RECT_HEIGHT$1 extends FloatPropertyCompat<Rect> {
    FloatProperties$Companion$RECT_HEIGHT$1() {
        super("RectHeight");
    }

    public float getValue(Rect rect) {
        Intrinsics.checkNotNullParameter(rect, "rect");
        return (float) rect.height();
    }

    public void setValue(Rect rect, float f) {
        Intrinsics.checkNotNullParameter(rect, "rect");
        rect.bottom = rect.top + ((int) f);
    }
}
