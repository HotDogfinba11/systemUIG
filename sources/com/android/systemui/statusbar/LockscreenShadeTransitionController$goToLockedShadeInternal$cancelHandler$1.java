package com.android.systemui.statusbar;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* access modifiers changed from: package-private */
/* compiled from: LockscreenShadeTransitionController.kt */
public final class LockscreenShadeTransitionController$goToLockedShadeInternal$cancelHandler$1 implements Runnable {
    final /* synthetic */ Runnable $cancelAction;
    final /* synthetic */ LockscreenShadeTransitionController this$0;

    LockscreenShadeTransitionController$goToLockedShadeInternal$cancelHandler$1(LockscreenShadeTransitionController lockscreenShadeTransitionController, Runnable runnable) {
        this.this$0 = lockscreenShadeTransitionController;
        this.$cancelAction = runnable;
    }

    public final void run() {
        NotificationEntry notificationEntry = this.this$0.draggedDownEntry;
        if (notificationEntry != null) {
            LockscreenShadeTransitionController lockscreenShadeTransitionController = this.this$0;
            notificationEntry.setUserLocked(false);
            notificationEntry.notifyHeightChanged(false);
            lockscreenShadeTransitionController.draggedDownEntry = null;
        }
        Runnable runnable = this.$cancelAction;
        if (runnable != null) {
            runnable.run();
        }
    }
}
