package com.android.keyguard;

import android.graphics.Typeface;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* access modifiers changed from: package-private */
/* compiled from: TextAnimator.kt */
public final class TextAnimator$setTextStyle$1 extends Lambda implements Function0<Typeface> {
    final /* synthetic */ int $weight;
    final /* synthetic */ TextAnimator this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    TextAnimator$setTextStyle$1(TextAnimator textAnimator, int i) {
        super(0);
        this.this$0 = textAnimator;
        this.$weight = i;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Typeface invoke() {
        this.this$0.getTextInterpolator$frameworks__base__packages__SystemUI__android_common__SystemUI_core().getTargetPaint().setFontVariationSettings(Intrinsics.stringPlus("'wght' ", Integer.valueOf(this.$weight)));
        return this.this$0.getTextInterpolator$frameworks__base__packages__SystemUI__android_common__SystemUI_core().getTargetPaint().getTypeface();
    }
}
