package com.android.systemui.media;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

/* compiled from: LightSourceDrawable.kt */
public final class LightSourceDrawable$illuminate$1$3 extends AnimatorListenerAdapter {
    final /* synthetic */ LightSourceDrawable this$0;

    LightSourceDrawable$illuminate$1$3(LightSourceDrawable lightSourceDrawable) {
        this.this$0 = lightSourceDrawable;
    }

    public void onAnimationEnd(Animator animator) {
        this.this$0.rippleData.setProgress(0.0f);
        this.this$0.rippleAnimation = null;
        this.this$0.invalidateSelf();
    }
}
