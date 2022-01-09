package com.android.wm.shell.bubbles.animation;

import com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout;

public final /* synthetic */ class StackAnimationController$$ExternalSyntheticLambda2 implements PhysicsAnimationLayout.PhysicsAnimationController.ChildAnimationConfigurator {
    public final /* synthetic */ StackAnimationController f$0;
    public final /* synthetic */ float f$1;

    public /* synthetic */ StackAnimationController$$ExternalSyntheticLambda2(StackAnimationController stackAnimationController, float f) {
        this.f$0 = stackAnimationController;
        this.f$1 = f;
    }

    @Override // com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout.PhysicsAnimationController.ChildAnimationConfigurator
    public final void configureAnimationForChildAtIndex(int i, PhysicsAnimationLayout.PhysicsPropertyAnimator physicsPropertyAnimator) {
        StackAnimationController.$r8$lambda$4Zz3rmRClyzsQBYGekkqAy1sX_8(this.f$0, this.f$1, i, physicsPropertyAnimator);
    }
}
