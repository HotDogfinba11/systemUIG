package com.android.systemui.statusbar.phone;

import com.android.systemui.plugins.ActivityStarter;

public final /* synthetic */ class StatusBar$$ExternalSyntheticLambda6 implements KeyguardDismissHandler {
    public final /* synthetic */ StatusBar f$0;

    public /* synthetic */ StatusBar$$ExternalSyntheticLambda6(StatusBar statusBar) {
        this.f$0 = statusBar;
    }

    @Override // com.android.systemui.statusbar.phone.KeyguardDismissHandler
    public final void executeWhenUnlocked(ActivityStarter.OnDismissAction onDismissAction, boolean z, boolean z2) {
        this.f$0.executeWhenUnlocked(onDismissAction, z, z2);
    }
}
