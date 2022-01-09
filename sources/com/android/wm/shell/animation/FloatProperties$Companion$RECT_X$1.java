package com.android.wm.shell.animation;

import android.graphics.Rect;
import androidx.dynamicanimation.animation.FloatPropertyCompat;

/* compiled from: FloatProperties.kt */
public final class FloatProperties$Companion$RECT_X$1 extends FloatPropertyCompat<Rect> {
    FloatProperties$Companion$RECT_X$1() {
        super("RectX");
    }

    public void setValue(Rect rect, float f) {
        if (rect != null) {
            rect.offsetTo((int) f, rect.top);
        }
    }

    public float getValue(Rect rect) {
        Integer valueOf = rect == null ? null : Integer.valueOf(rect.left);
        if (valueOf == null) {
            return -3.4028235E38f;
        }
        return (float) valueOf.intValue();
    }
}
