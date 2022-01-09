package com.android.systemui.media;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

/* compiled from: LightSourceDrawable.kt */
public final class LightSourceDrawable$active$1$2 extends AnimatorListenerAdapter {
    private boolean cancelled;
    final /* synthetic */ LightSourceDrawable this$0;

    LightSourceDrawable$active$1$2(LightSourceDrawable lightSourceDrawable) {
        this.this$0 = lightSourceDrawable;
    }

    public void onAnimationCancel(Animator animator) {
        this.cancelled = true;
    }

    public void onAnimationEnd(Animator animator) {
        if (!this.cancelled) {
            this.this$0.rippleData.setProgress(0.0f);
            this.this$0.rippleData.setAlpha(0.0f);
            this.this$0.rippleAnimation = null;
            this.this$0.invalidateSelf();
        }
    }
}
