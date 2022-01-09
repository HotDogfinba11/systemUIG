package com.google.android.systemui.columbus.sensors.config;

import android.content.Context;
import com.android.systemui.R$integer;

public class SensorConfiguration {
    public final float defaultSensitivityValue;
    public final float lowSensitivityValue;

    SensorConfiguration(Context context) {
        this.defaultSensitivityValue = ((float) context.getResources().getInteger(R$integer.columbus_default_sensitivity_percent)) * 0.01f;
        this.lowSensitivityValue = ((float) context.getResources().getInteger(R$integer.columbus_low_sensitivity_percent)) * 0.01f;
    }
}
