package com.android.systemui.navigationbar;

import android.view.accessibility.AccessibilityManager;

public final /* synthetic */ class NavigationBar$$ExternalSyntheticLambda11 implements AccessibilityManager.AccessibilityServicesStateChangeListener {
    public final /* synthetic */ NavigationBar f$0;

    public /* synthetic */ NavigationBar$$ExternalSyntheticLambda11(NavigationBar navigationBar) {
        this.f$0 = navigationBar;
    }

    public final void onAccessibilityServicesStateChanged(AccessibilityManager accessibilityManager) {
        this.f$0.updateAccessibilityServicesState(accessibilityManager);
    }
}
