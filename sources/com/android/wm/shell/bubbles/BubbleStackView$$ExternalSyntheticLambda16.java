package com.android.wm.shell.bubbles;

import android.util.ArrayMap;
import com.android.wm.shell.animation.PhysicsAnimator;
import com.android.wm.shell.bubbles.animation.AnimatableScaleMatrix;

public final /* synthetic */ class BubbleStackView$$ExternalSyntheticLambda16 implements PhysicsAnimator.UpdateListener {
    public final /* synthetic */ BubbleStackView f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ float f$2;

    public /* synthetic */ BubbleStackView$$ExternalSyntheticLambda16(BubbleStackView bubbleStackView, boolean z, float f) {
        this.f$0 = bubbleStackView;
        this.f$1 = z;
        this.f$2 = f;
    }

    @Override // com.android.wm.shell.animation.PhysicsAnimator.UpdateListener
    public final void onAnimationUpdateForProperty(Object obj, ArrayMap arrayMap) {
        this.f$0.lambda$animateExpansion$23(this.f$1, this.f$2, (AnimatableScaleMatrix) obj, arrayMap);
    }
}
