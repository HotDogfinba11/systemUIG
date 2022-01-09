package com.google.android.systemui.reversecharging;

import android.os.Bundle;
import com.google.android.systemui.reversecharging.ReverseWirelessCharger;

public final /* synthetic */ class ReverseChargingController$$ExternalSyntheticLambda7 implements ReverseWirelessCharger.ReverseChargingChangeListener {
    public final /* synthetic */ ReverseChargingController f$0;

    public /* synthetic */ ReverseChargingController$$ExternalSyntheticLambda7(ReverseChargingController reverseChargingController) {
        this.f$0 = reverseChargingController;
    }

    @Override // com.google.android.systemui.reversecharging.ReverseWirelessCharger.ReverseChargingChangeListener
    public final void onReverseStatusChanged(Bundle bundle) {
        this.f$0.onReverseStateChanged(bundle);
    }
}
