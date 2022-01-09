package com.android.wm.shell.pip.phone;

import android.graphics.Rect;
import android.util.ArrayMap;
import com.android.wm.shell.animation.PhysicsAnimator;

public final /* synthetic */ class PipMotionHelper$$ExternalSyntheticLambda0 implements PhysicsAnimator.UpdateListener {
    public final /* synthetic */ PipMotionHelper f$0;

    public /* synthetic */ PipMotionHelper$$ExternalSyntheticLambda0(PipMotionHelper pipMotionHelper) {
        this.f$0 = pipMotionHelper;
    }

    @Override // com.android.wm.shell.animation.PhysicsAnimator.UpdateListener
    public final void onAnimationUpdateForProperty(Object obj, ArrayMap arrayMap) {
        this.f$0.lambda$new$2((Rect) obj, arrayMap);
    }
}
