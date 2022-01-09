package com.android.systemui.media;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

/* compiled from: IlluminationDrawable.kt */
public final class IlluminationDrawable$animateBackground$1$2 extends AnimatorListenerAdapter {
    final /* synthetic */ IlluminationDrawable this$0;

    IlluminationDrawable$animateBackground$1$2(IlluminationDrawable illuminationDrawable) {
        this.this$0 = illuminationDrawable;
    }

    public void onAnimationEnd(Animator animator) {
        IlluminationDrawable.access$setBackgroundAnimation$p(this.this$0, null);
    }
}
