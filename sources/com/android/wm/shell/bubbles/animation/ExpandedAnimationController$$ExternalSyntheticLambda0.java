package com.android.wm.shell.bubbles.animation;

import com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout;

public final /* synthetic */ class ExpandedAnimationController$$ExternalSyntheticLambda0 implements PhysicsAnimationLayout.PhysicsAnimationController.ChildAnimationConfigurator {
    public final /* synthetic */ ExpandedAnimationController f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ ExpandedAnimationController$$ExternalSyntheticLambda0(ExpandedAnimationController expandedAnimationController, boolean z) {
        this.f$0 = expandedAnimationController;
        this.f$1 = z;
    }

    @Override // com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout.PhysicsAnimationController.ChildAnimationConfigurator
    public final void configureAnimationForChildAtIndex(int i, PhysicsAnimationLayout.PhysicsPropertyAnimator physicsPropertyAnimator) {
        ExpandedAnimationController.m525$r8$lambda$GKcras8sSgXfvJ9BCmeWKVCtM(this.f$0, this.f$1, i, physicsPropertyAnimator);
    }
}
