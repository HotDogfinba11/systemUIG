package com.google.android.systemui.reversecharging;

import android.os.Bundle;
import com.google.android.systemui.reversecharging.ReverseWirelessCharger;

public final /* synthetic */ class ReverseChargingController$$ExternalSyntheticLambda8 implements ReverseWirelessCharger.ReverseChargingInformationChangeListener {
    public final /* synthetic */ ReverseChargingController f$0;

    public /* synthetic */ ReverseChargingController$$ExternalSyntheticLambda8(ReverseChargingController reverseChargingController) {
        this.f$0 = reverseChargingController;
    }

    @Override // com.google.android.systemui.reversecharging.ReverseWirelessCharger.ReverseChargingInformationChangeListener
    public final void onReverseInformationChanged(Bundle bundle) {
        this.f$0.onReverseInformationChanged(bundle);
    }
}
