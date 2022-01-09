package com.android.wm.shell.animation;

import androidx.dynamicanimation.animation.AnimationHandler;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import androidx.dynamicanimation.animation.SpringAnimation;
import com.android.wm.shell.animation.PhysicsAnimator;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PhysicsAnimator.kt */
public final class PhysicsAnimator$startInternal$3 implements PhysicsAnimator.EndListener<T> {
    final /* synthetic */ FloatPropertyCompat<? super T> $animatedProperty;
    final /* synthetic */ float $flingMax;
    final /* synthetic */ float $flingMin;
    final /* synthetic */ PhysicsAnimator.SpringConfig $springConfig;
    final /* synthetic */ PhysicsAnimator<T> this$0;

    PhysicsAnimator$startInternal$3(FloatPropertyCompat<? super T> floatPropertyCompat, float f, float f2, PhysicsAnimator.SpringConfig springConfig, PhysicsAnimator<T> physicsAnimator) {
        this.$animatedProperty = floatPropertyCompat;
        this.$flingMin = f;
        this.$flingMax = f2;
        this.$springConfig = springConfig;
        this.this$0 = physicsAnimator;
    }

    @Override // com.android.wm.shell.animation.PhysicsAnimator.EndListener
    public void onAnimationEnd(T t, FloatPropertyCompat<? super T> floatPropertyCompat, boolean z, boolean z2, float f, float f2, boolean z3) {
        Intrinsics.checkNotNullParameter(floatPropertyCompat, "property");
        if (Intrinsics.areEqual(floatPropertyCompat, this.$animatedProperty) && z && !z2) {
            boolean z4 = true;
            boolean z5 = Math.abs(f2) > 0.0f;
            boolean z6 = !(this.$flingMin <= f && f <= this.$flingMax);
            if (z5 || z6) {
                this.$springConfig.setStartVelocity$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell(f2);
                if (this.$springConfig.getFinalPosition$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell() != PhysicsAnimatorKt.UNSET) {
                    z4 = false;
                }
                if (z4) {
                    if (z5) {
                        this.$springConfig.setFinalPosition$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell(f2 < 0.0f ? this.$flingMin : this.$flingMax);
                    } else if (z6) {
                        PhysicsAnimator.SpringConfig springConfig = this.$springConfig;
                        float f3 = this.$flingMin;
                        if (f >= f3) {
                            f3 = this.$flingMax;
                        }
                        springConfig.setFinalPosition$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell(f3);
                    }
                }
                SpringAnimation springAnimation = this.this$0.getSpringAnimation(this.$animatedProperty, t);
                AnimationHandler animationHandler = this.this$0.customAnimationHandler;
                if (animationHandler == null) {
                    animationHandler = springAnimation.getAnimationHandler();
                    Intrinsics.checkNotNullExpressionValue(animationHandler, "springAnim.animationHandler");
                }
                springAnimation.setAnimationHandler(animationHandler);
                this.$springConfig.applyToAnimation$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell(springAnimation);
                springAnimation.start();
            }
        }
    }
}
