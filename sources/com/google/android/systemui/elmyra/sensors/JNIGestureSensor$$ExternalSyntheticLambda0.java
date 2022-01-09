package com.google.android.systemui.elmyra.sensors;

import com.google.android.systemui.elmyra.sensors.config.GestureConfiguration;

public final /* synthetic */ class JNIGestureSensor$$ExternalSyntheticLambda0 implements GestureConfiguration.Listener {
    public final /* synthetic */ JNIGestureSensor f$0;

    public /* synthetic */ JNIGestureSensor$$ExternalSyntheticLambda0(JNIGestureSensor jNIGestureSensor) {
        this.f$0 = jNIGestureSensor;
    }

    @Override // com.google.android.systemui.elmyra.sensors.config.GestureConfiguration.Listener
    public final void onGestureConfigurationChanged(GestureConfiguration gestureConfiguration) {
        this.f$0.lambda$new$0(gestureConfiguration);
    }
}
