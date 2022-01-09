package com.google.android.systemui.columbus.feedback;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.animation.DecelerateInterpolator;
import com.android.systemui.assist.AssistManager;
import com.google.android.systemui.assist.AssistManagerGoogle;
import com.google.android.systemui.columbus.sensors.GestureSensor;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class AssistInvocationEffect implements FeedbackEffect {
    public static final Companion Companion = new Companion(null);
    private Animator animation;
    private final AssistInvocationEffect$animatorListener$1 animatorListener;
    private final ValueAnimator.AnimatorUpdateListener animatorUpdateListener;
    private final AssistManagerGoogle assistManager;
    private float progress;

    public AssistInvocationEffect(AssistManager assistManager2) {
        Intrinsics.checkNotNullParameter(assistManager2, "assistManager");
        this.assistManager = assistManager2 instanceof AssistManagerGoogle ? (AssistManagerGoogle) assistManager2 : null;
        this.animatorUpdateListener = new AssistInvocationEffect$animatorUpdateListener$1(this);
        this.animatorListener = new AssistInvocationEffect$animatorListener$1(this);
    }

    @Override // com.google.android.systemui.columbus.feedback.FeedbackEffect
    public void onGestureDetected(int i, GestureSensor.DetectionProperties detectionProperties) {
        if (i == 0) {
            clear();
        } else if (i == 1) {
            onTrigger();
        }
    }

    private final void onTrigger() {
        clear();
        ValueAnimator ofFloat = ValueAnimator.ofFloat(this.progress, 1.0f);
        ofFloat.setDuration(200L);
        ofFloat.setInterpolator(new DecelerateInterpolator());
        ofFloat.addUpdateListener(this.animatorUpdateListener);
        ofFloat.addListener(this.animatorListener);
        this.animation = ofFloat;
        ofFloat.start();
    }

    private final void clear() {
        Animator animator;
        Animator animator2 = this.animation;
        if (Intrinsics.areEqual(animator2 == null ? null : Boolean.valueOf(animator2.isRunning()), Boolean.TRUE) && (animator = this.animation) != null) {
            animator.cancel();
        }
        this.animation = null;
    }

    private final void updateAssistManager() {
        AssistManagerGoogle assistManagerGoogle = this.assistManager;
        if (assistManagerGoogle != null) {
            assistManagerGoogle.onInvocationProgress(2, this.progress);
        }
    }

    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
