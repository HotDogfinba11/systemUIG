package com.android.wm.shell.animation;

import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FlingAnimation;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* access modifiers changed from: package-private */
/* compiled from: PhysicsAnimator.kt */
public final class PhysicsAnimator$configureDynamicAnimation$2 implements DynamicAnimation.OnAnimationEndListener {
    final /* synthetic */ DynamicAnimation<?> $anim;
    final /* synthetic */ FloatPropertyCompat<? super T> $property;
    final /* synthetic */ PhysicsAnimator<T> this$0;

    PhysicsAnimator$configureDynamicAnimation$2(PhysicsAnimator<T> physicsAnimator, FloatPropertyCompat<? super T> floatPropertyCompat, DynamicAnimation<?> dynamicAnimation) {
        this.this$0 = physicsAnimator;
        this.$property = floatPropertyCompat;
        this.$anim = dynamicAnimation;
    }

    @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener
    public final void onAnimationEnd(DynamicAnimation dynamicAnimation, final boolean z, final float f, final float f2) {
        ArrayList<PhysicsAnimator<T>.InternalListener> internalListeners$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell = this.this$0.getInternalListeners$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell();
        final FloatPropertyCompat<? super T> floatPropertyCompat = this.$property;
        final DynamicAnimation<?> dynamicAnimation2 = this.$anim;
        boolean unused = CollectionsKt__MutableCollectionsKt.removeAll((List) internalListeners$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell, (Function1) new Function1<PhysicsAnimator<T>.InternalListener, Boolean>() {
            /* class com.android.wm.shell.animation.PhysicsAnimator$configureDynamicAnimation$2.AnonymousClass1 */

            /* Return type fixed from 'java.lang.Object' to match base method */
            /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object] */
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Boolean invoke(PhysicsAnimator<T>.InternalListener internalListener) {
                return Boolean.valueOf(invoke(internalListener));
            }

            public final boolean invoke(PhysicsAnimator<T>.InternalListener internalListener) {
                Intrinsics.checkNotNullParameter(internalListener, "it");
                return internalListener.onInternalAnimationEnd$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell(floatPropertyCompat, z, f, f2, dynamicAnimation2 instanceof FlingAnimation);
            }
        });
        if (Intrinsics.areEqual(this.this$0.springAnimations.get(this.$property), this.$anim)) {
            this.this$0.springAnimations.remove(this.$property);
        }
        if (Intrinsics.areEqual(this.this$0.flingAnimations.get(this.$property), this.$anim)) {
            this.this$0.flingAnimations.remove(this.$property);
        }
    }
}
