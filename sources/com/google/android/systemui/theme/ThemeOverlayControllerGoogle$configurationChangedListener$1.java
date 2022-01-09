package com.google.android.systemui.theme;

import com.android.systemui.statusbar.policy.ConfigurationController;

/* compiled from: ThemeOverlayControllerGoogle.kt */
public final class ThemeOverlayControllerGoogle$configurationChangedListener$1 implements ConfigurationController.ConfigurationListener {
    final /* synthetic */ ThemeOverlayControllerGoogle this$0;

    ThemeOverlayControllerGoogle$configurationChangedListener$1(ThemeOverlayControllerGoogle themeOverlayControllerGoogle) {
        this.this$0 = themeOverlayControllerGoogle;
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public void onOverlayChanged() {
        this.this$0.setBootColorSystemProps();
    }
}
