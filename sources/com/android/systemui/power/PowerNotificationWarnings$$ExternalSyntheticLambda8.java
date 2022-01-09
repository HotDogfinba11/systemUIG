package com.android.systemui.power;

import com.android.systemui.plugins.ActivityStarter;

public final /* synthetic */ class PowerNotificationWarnings$$ExternalSyntheticLambda8 implements ActivityStarter.Callback {
    public final /* synthetic */ PowerNotificationWarnings f$0;

    public /* synthetic */ PowerNotificationWarnings$$ExternalSyntheticLambda8(PowerNotificationWarnings powerNotificationWarnings) {
        this.f$0 = powerNotificationWarnings;
    }

    @Override // com.android.systemui.plugins.ActivityStarter.Callback
    public final void onActivityStarted(int i) {
        this.f$0.lambda$showUsbHighTemperatureAlarmInternal$4(i);
    }
}
