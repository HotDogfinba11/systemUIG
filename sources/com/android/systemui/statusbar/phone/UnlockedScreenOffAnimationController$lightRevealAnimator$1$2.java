package com.android.systemui.statusbar.phone;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import com.android.systemui.statusbar.LightRevealScrim;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UnlockedScreenOffAnimationController.kt */
public final class UnlockedScreenOffAnimationController$lightRevealAnimator$1$2 extends AnimatorListenerAdapter {
    final /* synthetic */ UnlockedScreenOffAnimationController this$0;

    UnlockedScreenOffAnimationController$lightRevealAnimator$1$2(UnlockedScreenOffAnimationController unlockedScreenOffAnimationController) {
        this.this$0 = unlockedScreenOffAnimationController;
    }

    public void onAnimationCancel(Animator animator) {
        LightRevealScrim lightRevealScrim = this.this$0.lightRevealScrim;
        if (lightRevealScrim != null) {
            lightRevealScrim.setRevealAmount(1.0f);
            this.this$0.lightRevealAnimationPlaying = false;
            this.this$0.sendUnlockedScreenOffProgressUpdate(0.0f, 0.0f);
            return;
        }
        Intrinsics.throwUninitializedPropertyAccessException("lightRevealScrim");
        throw null;
    }

    public void onAnimationEnd(Animator animator) {
        this.this$0.lightRevealAnimationPlaying = false;
    }
}
