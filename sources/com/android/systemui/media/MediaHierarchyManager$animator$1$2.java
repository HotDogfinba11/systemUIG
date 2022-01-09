package com.android.systemui.media;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;

/* compiled from: MediaHierarchyManager.kt */
public final class MediaHierarchyManager$animator$1$2 extends AnimatorListenerAdapter {
    private boolean cancelled;
    final /* synthetic */ MediaHierarchyManager this$0;

    MediaHierarchyManager$animator$1$2(MediaHierarchyManager mediaHierarchyManager) {
        this.this$0 = mediaHierarchyManager;
    }

    public void onAnimationCancel(Animator animator) {
        this.cancelled = true;
        this.this$0.animationPending = false;
        View view = this.this$0.rootView;
        if (view != null) {
            view.removeCallbacks(this.this$0.startAnimation);
        }
    }

    public void onAnimationEnd(Animator animator) {
        this.this$0.isCrossFadeAnimatorRunning = false;
        if (!this.cancelled) {
            this.this$0.applyTargetStateIfNotAnimating();
        }
    }

    public void onAnimationStart(Animator animator) {
        this.cancelled = false;
        this.this$0.animationPending = false;
    }
}
