package com.android.systemui.statusbar.phone;

import com.android.systemui.keyguard.KeyguardViewMediator;
import kotlin.jvm.internal.Intrinsics;

/* access modifiers changed from: package-private */
/* compiled from: UnlockedScreenOffAnimationController.kt */
public final class UnlockedScreenOffAnimationController$animateInKeyguard$1 implements Runnable {
    final /* synthetic */ Runnable $after;
    final /* synthetic */ UnlockedScreenOffAnimationController this$0;

    UnlockedScreenOffAnimationController$animateInKeyguard$1(UnlockedScreenOffAnimationController unlockedScreenOffAnimationController, Runnable runnable) {
        this.this$0 = unlockedScreenOffAnimationController;
        this.$after = runnable;
    }

    public final void run() {
        this.this$0.aodUiAnimationPlaying = false;
        ((KeyguardViewMediator) this.this$0.keyguardViewMediatorLazy.get()).maybeHandlePendingLock();
        StatusBar statusBar = this.this$0.statusBar;
        if (statusBar != null) {
            statusBar.updateIsKeyguard();
            this.$after.run();
            this.this$0.decidedToAnimateGoingToSleep = null;
            return;
        }
        Intrinsics.throwUninitializedPropertyAccessException("statusBar");
        throw null;
    }
}
