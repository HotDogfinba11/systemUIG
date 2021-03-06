package com.google.android.systemui.columbus.gates;

import com.android.systemui.util.sensors.ThresholdSensor;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Proximity.kt */
public final class Proximity$proximityListener$1 implements ThresholdSensor.Listener {
    final /* synthetic */ Proximity this$0;

    Proximity$proximityListener$1(Proximity proximity) {
        this.this$0 = proximity;
    }

    @Override // com.android.systemui.util.sensors.ThresholdSensor.Listener
    public void onThresholdCrossed(ThresholdSensor.ThresholdSensorEvent thresholdSensorEvent) {
        Intrinsics.checkNotNullParameter(thresholdSensorEvent, "event");
        this.this$0.updateBlocking();
    }
}
