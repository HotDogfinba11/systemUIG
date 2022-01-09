package com.android.wm.shell.bubbles;

import android.util.ArrayMap;
import com.android.wm.shell.animation.PhysicsAnimator;
import com.android.wm.shell.bubbles.animation.AnimatableScaleMatrix;

public final /* synthetic */ class BubbleStackView$$ExternalSyntheticLambda15 implements PhysicsAnimator.UpdateListener {
    public final /* synthetic */ BubbleStackView f$0;

    public /* synthetic */ BubbleStackView$$ExternalSyntheticLambda15(BubbleStackView bubbleStackView) {
        this.f$0 = bubbleStackView;
    }

    @Override // com.android.wm.shell.animation.PhysicsAnimator.UpdateListener
    public final void onAnimationUpdateForProperty(Object obj, ArrayMap arrayMap) {
        this.f$0.lambda$showExpandedViewIfNeeded$21((AnimatableScaleMatrix) obj, arrayMap);
    }
}
