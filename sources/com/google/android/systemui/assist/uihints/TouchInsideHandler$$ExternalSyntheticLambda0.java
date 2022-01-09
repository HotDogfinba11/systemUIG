package com.google.android.systemui.assist.uihints;

import com.android.systemui.navigationbar.NavigationModeController;

public final /* synthetic */ class TouchInsideHandler$$ExternalSyntheticLambda0 implements NavigationModeController.ModeChangedListener {
    public final /* synthetic */ TouchInsideHandler f$0;

    public /* synthetic */ TouchInsideHandler$$ExternalSyntheticLambda0(TouchInsideHandler touchInsideHandler) {
        this.f$0 = touchInsideHandler;
    }

    @Override // com.android.systemui.navigationbar.NavigationModeController.ModeChangedListener
    public final void onNavigationModeChanged(int i) {
        this.f$0.onNavigationModeChange(i);
    }
}
