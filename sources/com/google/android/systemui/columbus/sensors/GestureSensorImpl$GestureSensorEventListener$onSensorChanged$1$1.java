package com.google.android.systemui.columbus.sensors;

import com.google.android.systemui.columbus.sensors.GestureSensor;

/* compiled from: GestureSensorImpl.kt */
final class GestureSensorImpl$GestureSensorEventListener$onSensorChanged$1$1 implements Runnable {
    final /* synthetic */ GestureSensorImpl this$0;

    GestureSensorImpl$GestureSensorEventListener$onSensorChanged$1$1(GestureSensorImpl gestureSensorImpl) {
        this.this$0 = gestureSensorImpl;
    }

    public final void run() {
        this.this$0.reportGestureDetected(2, new GestureSensor.DetectionProperties(true));
    }
}
