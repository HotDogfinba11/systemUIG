package com.google.android.systemui.elmyra.gates;

import android.content.Context;
import com.android.systemui.Dependency;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.google.android.systemui.elmyra.actions.Action;
import java.util.ArrayList;
import java.util.List;

public class SetupWizard extends Gate {
    private final List<Action> mExceptions;
    private final DeviceProvisionedController mProvisionedController;
    private final DeviceProvisionedController.DeviceProvisionedListener mProvisionedListener = new DeviceProvisionedController.DeviceProvisionedListener() {
        /* class com.google.android.systemui.elmyra.gates.SetupWizard.AnonymousClass1 */

        @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
        public void onDeviceProvisionedChanged() {
            updateSetupComplete();
        }

        @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
        public void onUserSetupChanged() {
            updateSetupComplete();
        }

        private void updateSetupComplete() {
            boolean isSetupComplete = SetupWizard.this.isSetupComplete();
            if (isSetupComplete != SetupWizard.this.mSetupComplete) {
                SetupWizard.this.mSetupComplete = isSetupComplete;
                SetupWizard.this.notifyListener();
            }
        }
    };
    private boolean mSetupComplete;

    public SetupWizard(Context context, List<Action> list) {
        super(context);
        this.mExceptions = new ArrayList(list);
        this.mProvisionedController = (DeviceProvisionedController) Dependency.get(DeviceProvisionedController.class);
    }

    /* access modifiers changed from: protected */
    @Override // com.google.android.systemui.elmyra.gates.Gate
    public void onActivate() {
        this.mSetupComplete = isSetupComplete();
        this.mProvisionedController.addCallback(this.mProvisionedListener);
    }

    /* access modifiers changed from: protected */
    @Override // com.google.android.systemui.elmyra.gates.Gate
    public void onDeactivate() {
        this.mProvisionedController.removeCallback(this.mProvisionedListener);
    }

    /* access modifiers changed from: protected */
    @Override // com.google.android.systemui.elmyra.gates.Gate
    public boolean isBlocked() {
        for (int i = 0; i < this.mExceptions.size(); i++) {
            if (this.mExceptions.get(i).isAvailable()) {
                return false;
            }
        }
        return !this.mSetupComplete;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean isSetupComplete() {
        return this.mProvisionedController.isDeviceProvisioned() && this.mProvisionedController.isCurrentUserSetup();
    }

    @Override // com.google.android.systemui.elmyra.gates.Gate
    public String toString() {
        return super.toString() + " [isDeviceProvisioned -> " + this.mProvisionedController.isDeviceProvisioned() + "; isCurrentUserSetup -> " + this.mProvisionedController.isCurrentUserSetup() + "]";
    }
}
