package com.android.wm.shell.animation;

import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FloatPropertyCompat;

/* access modifiers changed from: package-private */
/* compiled from: PhysicsAnimator.kt */
public final class PhysicsAnimator$configureDynamicAnimation$1 implements DynamicAnimation.OnAnimationUpdateListener {
    final /* synthetic */ FloatPropertyCompat<? super T> $property;
    final /* synthetic */ PhysicsAnimator<T> this$0;

    PhysicsAnimator$configureDynamicAnimation$1(PhysicsAnimator<T> physicsAnimator, FloatPropertyCompat<? super T> floatPropertyCompat) {
        this.this$0 = physicsAnimator;
        this.$property = floatPropertyCompat;
    }

    @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
    public final void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f, float f2) {
        int size = this.this$0.getInternalListeners$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell().size();
        if (size > 0) {
            int i = 0;
            while (true) {
                int i2 = i + 1;
                this.this$0.getInternalListeners$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell().get(i).onInternalAnimationUpdate$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell(this.$property, f, f2);
                if (i2 < size) {
                    i = i2;
                } else {
                    return;
                }
            }
        }
    }
}
