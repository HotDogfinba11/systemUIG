package com.android.systemui.statusbar;

import com.android.systemui.plugins.ActivityStarter;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* access modifiers changed from: package-private */
/* compiled from: LockscreenShadeTransitionController.kt */
public final class LockscreenShadeTransitionController$goToLockedShadeInternal$1 implements ActivityStarter.OnDismissAction {
    final /* synthetic */ Function1<Long, Unit> $animationHandler;
    final /* synthetic */ LockscreenShadeTransitionController this$0;

    /* JADX DEBUG: Multi-variable search result rejected for r2v0, resolved type: kotlin.jvm.functions.Function1<? super java.lang.Long, kotlin.Unit> */
    /* JADX WARN: Multi-variable type inference failed */
    LockscreenShadeTransitionController$goToLockedShadeInternal$1(LockscreenShadeTransitionController lockscreenShadeTransitionController, Function1<? super Long, Unit> function1) {
        this.this$0 = lockscreenShadeTransitionController;
        this.$animationHandler = function1;
    }

    @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
    public final boolean onDismiss() {
        this.this$0.animationHandlerOnKeyguardDismiss = this.$animationHandler;
        return false;
    }
}
