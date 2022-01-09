package com.android.systemui.statusbar.phone;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: UnlockedScreenOffAnimationController.kt */
final class UnlockedScreenOffAnimationController$onStartedGoingToSleep$1 implements Runnable {
    final /* synthetic */ UnlockedScreenOffAnimationController this$0;

    UnlockedScreenOffAnimationController$onStartedGoingToSleep$1(UnlockedScreenOffAnimationController unlockedScreenOffAnimationController) {
        this.this$0 = unlockedScreenOffAnimationController;
    }

    public final void run() {
        this.this$0.aodUiAnimationPlaying = true;
        StatusBar statusBar = this.this$0.statusBar;
        if (statusBar != null) {
            statusBar.getNotificationPanelViewController().showAodUi();
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("statusBar");
            throw null;
        }
    }
}
