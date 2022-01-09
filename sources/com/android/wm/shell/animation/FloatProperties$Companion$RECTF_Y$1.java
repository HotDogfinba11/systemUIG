package com.android.wm.shell.animation;

import android.graphics.RectF;
import androidx.dynamicanimation.animation.FloatPropertyCompat;

/* compiled from: FloatProperties.kt */
public final class FloatProperties$Companion$RECTF_Y$1 extends FloatPropertyCompat<RectF> {
    FloatProperties$Companion$RECTF_Y$1() {
        super("RectFY");
    }

    public void setValue(RectF rectF, float f) {
        if (rectF != null) {
            rectF.offsetTo(rectF.left, f);
        }
    }

    public float getValue(RectF rectF) {
        if (rectF == null) {
            return -3.4028235E38f;
        }
        return rectF.top;
    }
}
