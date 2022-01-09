package com.google.android.systemui.columbus.sensors;

import com.google.android.systemui.columbus.sensors.GestureSensor;

/* compiled from: GestureSensorImpl.kt */
final class GestureSensorImpl$GestureSensorEventListener$onSensorChanged$1$2 implements Runnable {
    final /* synthetic */ GestureSensorImpl this$0;

    GestureSensorImpl$GestureSensorEventListener$onSensorChanged$1$2(GestureSensorImpl gestureSensorImpl) {
        this.this$0 = gestureSensorImpl;
    }

    public final void run() {
        this.this$0.reportGestureDetected(1, new GestureSensor.DetectionProperties(false));
    }
}
