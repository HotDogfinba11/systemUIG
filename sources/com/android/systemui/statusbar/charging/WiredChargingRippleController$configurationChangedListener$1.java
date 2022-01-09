package com.android.systemui.statusbar.charging;

import android.content.res.Configuration;
import com.android.systemui.R$dimen;
import com.android.systemui.statusbar.policy.ConfigurationController;

/* compiled from: WiredChargingRippleController.kt */
public final class WiredChargingRippleController$configurationChangedListener$1 implements ConfigurationController.ConfigurationListener {
    final /* synthetic */ WiredChargingRippleController this$0;

    WiredChargingRippleController$configurationChangedListener$1(WiredChargingRippleController wiredChargingRippleController) {
        this.this$0 = wiredChargingRippleController;
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

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public void onConfigChanged(Configuration configuration) {
        WiredChargingRippleController wiredChargingRippleController = this.this$0;
        wiredChargingRippleController.normalizedPortPosX = wiredChargingRippleController.context.getResources().getFloat(R$dimen.physical_charger_port_location_normalized_x);
        WiredChargingRippleController wiredChargingRippleController2 = this.this$0;
        wiredChargingRippleController2.normalizedPortPosY = wiredChargingRippleController2.context.getResources().getFloat(R$dimen.physical_charger_port_location_normalized_y);
    }
}
