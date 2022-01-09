package com.android.wm.shell.animation;

import android.graphics.Rect;
import androidx.dynamicanimation.animation.FloatPropertyCompat;

/* compiled from: FloatProperties.kt */
public final class FloatProperties$Companion$RECT_Y$1 extends FloatPropertyCompat<Rect> {
    FloatProperties$Companion$RECT_Y$1() {
        super("RectY");
    }

    public void setValue(Rect rect, float f) {
        if (rect != null) {
            rect.offsetTo(rect.left, (int) f);
        }
    }

    public float getValue(Rect rect) {
        Integer valueOf = rect == null ? null : Integer.valueOf(rect.top);
        if (valueOf == null) {
            return -3.4028235E38f;
        }
        return (float) valueOf.intValue();
    }
}
