package com.google.android.systemui.columbus.gates;

import android.content.Context;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.google.android.systemui.columbus.actions.Action;
import dagger.Lazy;
import java.util.Set;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SetupWizard.kt */
public final class SetupWizard extends Gate {
    private final SetupWizard$actionListener$1 actionListener = new SetupWizard$actionListener$1(this);
    private boolean exceptionActive;
    private final Set<Action> exceptions;
    private final Lazy<DeviceProvisionedController> provisionedController;
    private final SetupWizard$provisionedListener$1 provisionedListener = new SetupWizard$provisionedListener$1(this);
    private boolean setupComplete;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public SetupWizard(Context context, Set<Action> set, Lazy<DeviceProvisionedController> lazy) {
        super(context, null, 2, null);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(set, "exceptions");
        Intrinsics.checkNotNullParameter(lazy, "provisionedController");
        this.exceptions = set;
        this.provisionedController = lazy;
    }

    /* access modifiers changed from: protected */
    @Override // com.google.android.systemui.columbus.gates.Gate
    public void onActivate() {
        this.exceptionActive = false;
        for (T t : this.exceptions) {
            t.registerListener(this.actionListener);
            this.exceptionActive = t.isAvailable() | this.exceptionActive;
        }
        this.setupComplete = isSetupComplete();
        this.provisionedController.get().addCallback(this.provisionedListener);
        updateBlocking();
    }

    /* access modifiers changed from: protected */
    @Override // com.google.android.systemui.columbus.gates.Gate
    public void onDeactivate() {
        this.provisionedController.get().removeCallback(this.provisionedListener);
    }

    /* access modifiers changed from: private */
    public final void updateBlocking() {
        setBlocking(!this.exceptionActive && !this.setupComplete);
    }

    /* access modifiers changed from: private */
    public final boolean isSetupComplete() {
        return this.provisionedController.get().isDeviceProvisioned() && this.provisionedController.get().isCurrentUserSetup();
    }

    @Override // com.google.android.systemui.columbus.gates.Gate
    public String toString() {
        return super.toString() + " [isDeviceProvisioned -> " + this.provisionedController.get().isDeviceProvisioned() + "; isCurrentUserSetup -> " + this.provisionedController.get().isCurrentUserSetup() + ']';
    }
}
