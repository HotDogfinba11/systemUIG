package com.android.systemui.controls.ui;

import android.util.Log;
import com.android.systemui.controls.ui.ControlActionCoordinatorImpl;
import com.android.systemui.plugins.ActivityStarter;

/* compiled from: ControlActionCoordinatorImpl.kt */
final class ControlActionCoordinatorImpl$bouncerOrRun$1 implements ActivityStarter.OnDismissAction {
    final /* synthetic */ ControlActionCoordinatorImpl.Action $action;

    ControlActionCoordinatorImpl$bouncerOrRun$1(ControlActionCoordinatorImpl.Action action) {
        this.$action = action;
    }

    @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
    public final boolean onDismiss() {
        Log.d("ControlsUiController", "Device unlocked, invoking controls action");
        this.$action.invoke();
        return true;
    }
}
