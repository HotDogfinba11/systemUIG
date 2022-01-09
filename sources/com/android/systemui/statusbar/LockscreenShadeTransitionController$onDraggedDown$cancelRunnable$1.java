package com.android.systemui.statusbar;

/* access modifiers changed from: package-private */
/* compiled from: LockscreenShadeTransitionController.kt */
public final class LockscreenShadeTransitionController$onDraggedDown$cancelRunnable$1 implements Runnable {
    final /* synthetic */ LockscreenShadeTransitionController this$0;

    LockscreenShadeTransitionController$onDraggedDown$cancelRunnable$1(LockscreenShadeTransitionController lockscreenShadeTransitionController) {
        this.this$0 = lockscreenShadeTransitionController;
    }

    public final void run() {
        LockscreenShadeTransitionController.setDragDownAmountAnimated$default(this.this$0, 0.0f, 0, null, 6, null);
    }
}
