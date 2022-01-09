package com.android.wm.shell.animation;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* access modifiers changed from: package-private */
/* compiled from: PhysicsAnimator.kt */
public /* synthetic */ class PhysicsAnimator$startAction$1 extends FunctionReferenceImpl implements Function0<Unit> {
    PhysicsAnimator$startAction$1(PhysicsAnimator<T> physicsAnimator) {
        super(0, physicsAnimator, PhysicsAnimator.class, "startInternal", "startInternal$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final void invoke() {
        ((PhysicsAnimator) this.receiver).startInternal$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell();
    }
}
