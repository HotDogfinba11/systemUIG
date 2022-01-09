package com.android.systemui.statusbar.events;

import android.animation.ValueAnimator;
import kotlin.jvm.internal.Intrinsics;

/* access modifiers changed from: package-private */
/* compiled from: SystemStatusAnimationScheduler.kt */
public final class SystemStatusAnimationScheduler$chipUpdateListener$1 implements ValueAnimator.AnimatorUpdateListener {
    final /* synthetic */ SystemStatusAnimationScheduler this$0;

    SystemStatusAnimationScheduler$chipUpdateListener$1(SystemStatusAnimationScheduler systemStatusAnimationScheduler) {
        this.this$0 = systemStatusAnimationScheduler;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        SystemEventChipAnimationController systemEventChipAnimationController = this.this$0.chipAnimationController;
        Intrinsics.checkNotNullExpressionValue(valueAnimator, "anim");
        systemEventChipAnimationController.onChipAnimationUpdate(valueAnimator, this.this$0.getAnimationState());
    }
}
