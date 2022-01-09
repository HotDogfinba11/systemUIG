package com.android.systemui.statusbar;

import com.android.systemui.dock.DockManager;

public final /* synthetic */ class KeyguardIndicationController$$ExternalSyntheticLambda1 implements DockManager.AlignmentStateListener {
    public final /* synthetic */ KeyguardIndicationController f$0;

    public /* synthetic */ KeyguardIndicationController$$ExternalSyntheticLambda1(KeyguardIndicationController keyguardIndicationController) {
        this.f$0 = keyguardIndicationController;
    }

    @Override // com.android.systemui.dock.DockManager.AlignmentStateListener
    public final void onAlignmentStateChanged(int i) {
        this.f$0.lambda$init$1(i);
    }
}
