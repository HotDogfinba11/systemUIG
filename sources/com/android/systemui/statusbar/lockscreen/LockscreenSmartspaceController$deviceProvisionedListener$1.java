package com.android.systemui.statusbar.lockscreen;

import com.android.systemui.statusbar.policy.DeviceProvisionedController;

/* compiled from: LockscreenSmartspaceController.kt */
public final class LockscreenSmartspaceController$deviceProvisionedListener$1 implements DeviceProvisionedController.DeviceProvisionedListener {
    final /* synthetic */ LockscreenSmartspaceController this$0;

    LockscreenSmartspaceController$deviceProvisionedListener$1(LockscreenSmartspaceController lockscreenSmartspaceController) {
        this.this$0 = lockscreenSmartspaceController;
    }

    @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
    public void onDeviceProvisionedChanged() {
        this.this$0.connectSession();
    }

    @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
    public void onUserSetupChanged() {
        this.this$0.connectSession();
    }
}
