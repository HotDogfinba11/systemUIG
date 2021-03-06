package com.android.keyguard.dagger;

import com.android.keyguard.CarrierText;
import com.android.systemui.R$id;
import com.android.systemui.statusbar.phone.KeyguardStatusBarView;

public abstract class KeyguardStatusBarViewModule {
    static CarrierText getCarrierText(KeyguardStatusBarView keyguardStatusBarView) {
        return (CarrierText) keyguardStatusBarView.findViewById(R$id.keyguard_carrier_text);
    }
}
