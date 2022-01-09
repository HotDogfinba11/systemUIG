package com.google.android.systemui.elmyra.sensors;

import com.google.android.systemui.elmyra.sensors.config.GestureConfiguration;

public final /* synthetic */ class CHREGestureSensor$$ExternalSyntheticLambda1 implements GestureConfiguration.Listener {
    public final /* synthetic */ CHREGestureSensor f$0;

    public /* synthetic */ CHREGestureSensor$$ExternalSyntheticLambda1(CHREGestureSensor cHREGestureSensor) {
        this.f$0 = cHREGestureSensor;
    }

    @Override // com.google.android.systemui.elmyra.sensors.config.GestureConfiguration.Listener
    public final void onGestureConfigurationChanged(GestureConfiguration gestureConfiguration) {
        this.f$0.updateSensitivity(gestureConfiguration);
    }
}
