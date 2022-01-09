package com.android.systemui.util.sensors;

import com.android.systemui.util.sensors.ThresholdSensor;
import java.util.function.Consumer;

public final /* synthetic */ class ThresholdSensorImpl$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ boolean f$0;
    public final /* synthetic */ long f$1;

    public /* synthetic */ ThresholdSensorImpl$$ExternalSyntheticLambda0(boolean z, long j) {
        this.f$0 = z;
        this.f$1 = j;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ThresholdSensorImpl.$r8$lambda$_190uK9rLb4zpkN8EtAU_KFG8yY(this.f$0, this.f$1, (ThresholdSensor.Listener) obj);
    }
}
