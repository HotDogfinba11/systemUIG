package com.google.android.systemui.reversecharging;

import android.os.Bundle;
import com.google.android.systemui.reversecharging.ReverseWirelessCharger;

public final /* synthetic */ class ReverseChargingController$$ExternalSyntheticLambda6 implements ReverseWirelessCharger.IsDockPresentChangeListener {
    public final /* synthetic */ ReverseChargingController f$0;

    public /* synthetic */ ReverseChargingController$$ExternalSyntheticLambda6(ReverseChargingController reverseChargingController) {
        this.f$0 = reverseChargingController;
    }

    @Override // com.google.android.systemui.reversecharging.ReverseWirelessCharger.IsDockPresentChangeListener
    public final void onDockPresentChanged(Bundle bundle) {
        this.f$0.onDockPresentChanged(bundle);
    }
}
