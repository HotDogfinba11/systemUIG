package com.android.systemui.media;

import android.animation.ValueAnimator;
import com.android.internal.graphics.ColorUtils;
import java.util.ArrayList;
import java.util.Objects;

/* compiled from: IlluminationDrawable.kt */
final class IlluminationDrawable$animateBackground$1$1 implements ValueAnimator.AnimatorUpdateListener {
    final /* synthetic */ int $finalHighlight;
    final /* synthetic */ int $initialBackground;
    final /* synthetic */ int $initialHighlight;
    final /* synthetic */ IlluminationDrawable this$0;

    IlluminationDrawable$animateBackground$1$1(IlluminationDrawable illuminationDrawable, int i, int i2, int i3) {
        this.this$0 = illuminationDrawable;
        this.$initialBackground = i;
        this.$initialHighlight = i2;
        this.$finalHighlight = i3;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        Object animatedValue = valueAnimator.getAnimatedValue();
        Objects.requireNonNull(animatedValue, "null cannot be cast to non-null type kotlin.Float");
        float floatValue = ((Float) animatedValue).floatValue();
        IlluminationDrawable.access$getPaint$p(this.this$0).setColor(ColorUtils.blendARGB(this.$initialBackground, IlluminationDrawable.access$getBackgroundColor$p(this.this$0), floatValue));
        IlluminationDrawable.access$setHighlightColor$p(this.this$0, ColorUtils.blendARGB(this.$initialHighlight, this.$finalHighlight, floatValue));
        ArrayList<LightSourceDrawable> access$getLightSources$p = IlluminationDrawable.access$getLightSources$p(this.this$0);
        IlluminationDrawable illuminationDrawable = this.this$0;
        for (LightSourceDrawable lightSourceDrawable : access$getLightSources$p) {
            lightSourceDrawable.setHighlightColor(IlluminationDrawable.access$getHighlightColor$p(illuminationDrawable));
        }
        this.this$0.invalidateSelf();
    }
}
