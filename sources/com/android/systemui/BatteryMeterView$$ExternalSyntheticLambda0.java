package com.android.systemui;

import com.android.systemui.statusbar.policy.BatteryController;

public final /* synthetic */ class BatteryMeterView$$ExternalSyntheticLambda0 implements BatteryController.EstimateFetchCompletion {
    public final /* synthetic */ BatteryMeterView f$0;

    public /* synthetic */ BatteryMeterView$$ExternalSyntheticLambda0(BatteryMeterView batteryMeterView) {
        this.f$0 = batteryMeterView;
    }

    @Override // com.android.systemui.statusbar.policy.BatteryController.EstimateFetchCompletion
    public final void onBatteryRemainingEstimateRetrieved(String str) {
        BatteryMeterView.m41$r8$lambda$tETUFngFnD6fsDmUpqJf1miV_U(this.f$0, str);
    }
}
