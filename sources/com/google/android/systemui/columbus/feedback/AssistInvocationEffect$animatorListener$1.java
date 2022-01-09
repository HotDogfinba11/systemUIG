package com.google.android.systemui.columbus.feedback;

import android.animation.Animator;

/* compiled from: AssistInvocationEffect.kt */
public final class AssistInvocationEffect$animatorListener$1 implements Animator.AnimatorListener {
    final /* synthetic */ AssistInvocationEffect this$0;

    public void onAnimationRepeat(Animator animator) {
    }

    public void onAnimationStart(Animator animator) {
    }

    AssistInvocationEffect$animatorListener$1(AssistInvocationEffect assistInvocationEffect) {
        this.this$0 = assistInvocationEffect;
    }

    public void onAnimationEnd(Animator animator) {
        AssistInvocationEffect.access$setProgress$p(this.this$0, 0.0f);
    }

    public void onAnimationCancel(Animator animator) {
        AssistInvocationEffect.access$setProgress$p(this.this$0, 0.0f);
    }
}
