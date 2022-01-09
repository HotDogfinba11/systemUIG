package com.android.systemui.biometrics;

import android.content.res.Configuration;
import com.android.systemui.statusbar.policy.ConfigurationController;

/* compiled from: AuthRippleController.kt */
public final class AuthRippleController$configurationChangedListener$1 implements ConfigurationController.ConfigurationListener {
    final /* synthetic */ AuthRippleController this$0;

    AuthRippleController$configurationChangedListener$1(AuthRippleController authRippleController) {
        this.this$0 = authRippleController;
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public void onConfigChanged(Configuration configuration) {
        this.this$0.updateSensorLocation();
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public void onUiModeChanged() {
        this.this$0.updateRippleColor();
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public void onThemeChanged() {
        this.this$0.updateRippleColor();
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public void onOverlayChanged() {
        this.this$0.updateRippleColor();
    }
}
