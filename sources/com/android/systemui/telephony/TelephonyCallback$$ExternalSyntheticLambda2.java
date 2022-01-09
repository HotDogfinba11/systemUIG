package com.android.systemui.telephony;

import android.telephony.ServiceState;
import android.telephony.TelephonyCallback;
import java.util.function.Consumer;

public final /* synthetic */ class TelephonyCallback$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ ServiceState f$0;

    public /* synthetic */ TelephonyCallback$$ExternalSyntheticLambda2(ServiceState serviceState) {
        this.f$0 = serviceState;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        TelephonyCallback.$r8$lambda$vk_lKHWaAzI6c2pkXye62_KdExU(this.f$0, (TelephonyCallback.ServiceStateListener) obj);
    }
}
