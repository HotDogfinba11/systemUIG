package com.android.wm.shell.bubbles;

import android.graphics.PointF;
import android.view.View;
import com.android.wm.shell.R;
import com.android.wm.shell.animation.Interpolators;

/* access modifiers changed from: package-private */
/* compiled from: StackEducationView.kt */
public final class StackEducationView$show$1 implements Runnable {
    final /* synthetic */ PointF $stackPosition;
    final /* synthetic */ StackEducationView this$0;

    StackEducationView$show$1(StackEducationView stackEducationView, PointF pointF) {
        this.this$0 = stackEducationView;
        this.$stackPosition = pointF;
    }

    public final void run() {
        View view = this.this$0.getView();
        view.setTranslationY((this.$stackPosition.y + ((float) (view.getContext().getResources().getDimensionPixelSize(R.dimen.bubble_size) / 2))) - ((float) (view.getHeight() / 2)));
        this.this$0.animate().setDuration(this.this$0.ANIMATE_DURATION).setInterpolator(Interpolators.FAST_OUT_SLOW_IN).alpha(1.0f);
    }
}
