package com.android.systemui.controls.ui;

/* compiled from: ControlsUiControllerImpl.kt */
final class ControlsUiControllerImpl$onRefreshState$1$1$1$1 implements Runnable {
    final /* synthetic */ ControlWithState $cws;
    final /* synthetic */ boolean $isLocked;
    final /* synthetic */ ControlViewHolder $it;

    ControlsUiControllerImpl$onRefreshState$1$1$1$1(ControlViewHolder controlViewHolder, ControlWithState controlWithState, boolean z) {
        this.$it = controlViewHolder;
        this.$cws = controlWithState;
        this.$isLocked = z;
    }

    public final void run() {
        this.$it.bindData(this.$cws, this.$isLocked);
    }
}
