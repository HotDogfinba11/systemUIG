package com.android.systemui.power;

import android.content.DialogInterface;

public final /* synthetic */ class PowerNotificationWarnings$$ExternalSyntheticLambda5 implements DialogInterface.OnDismissListener {
    public final /* synthetic */ PowerNotificationWarnings f$0;

    public /* synthetic */ PowerNotificationWarnings$$ExternalSyntheticLambda5(PowerNotificationWarnings powerNotificationWarnings) {
        this.f$0 = powerNotificationWarnings;
    }

    public final void onDismiss(DialogInterface dialogInterface) {
        this.f$0.lambda$showHighTemperatureDialog$0(dialogInterface);
    }
}
