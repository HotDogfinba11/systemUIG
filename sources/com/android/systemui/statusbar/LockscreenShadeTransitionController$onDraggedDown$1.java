package com.android.systemui.statusbar;

import com.android.systemui.plugins.ActivityStarter;

/* access modifiers changed from: package-private */
/* compiled from: LockscreenShadeTransitionController.kt */
public final class LockscreenShadeTransitionController$onDraggedDown$1 implements ActivityStarter.OnDismissAction {
    final /* synthetic */ LockscreenShadeTransitionController this$0;

    LockscreenShadeTransitionController$onDraggedDown$1(LockscreenShadeTransitionController lockscreenShadeTransitionController) {
        this.this$0 = lockscreenShadeTransitionController;
    }

    @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
    public final boolean onDismiss() {
        this.this$0.nextHideKeyguardNeedsNoAnimation = true;
        return false;
    }
}
