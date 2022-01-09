package com.google.android.systemui.columbus.sensors.config;

import android.content.Context;
import com.google.android.systemui.columbus.ColumbusSettings;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: LowSensitivitySettingAdjustment.kt */
public final class LowSensitivitySettingAdjustment extends Adjustment {
    private final SensorConfiguration sensorConfiguration;
    private final LowSensitivitySettingAdjustment$settingsChangeListener$1 settingsChangeListener;
    private boolean useLowSensitivity;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public LowSensitivitySettingAdjustment(Context context, ColumbusSettings columbusSettings, SensorConfiguration sensorConfiguration2) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(columbusSettings, "columbusSettings");
        Intrinsics.checkNotNullParameter(sensorConfiguration2, "sensorConfiguration");
        this.sensorConfiguration = sensorConfiguration2;
        LowSensitivitySettingAdjustment$settingsChangeListener$1 lowSensitivitySettingAdjustment$settingsChangeListener$1 = new LowSensitivitySettingAdjustment$settingsChangeListener$1(this);
        this.settingsChangeListener = lowSensitivitySettingAdjustment$settingsChangeListener$1;
        columbusSettings.registerColumbusSettingsChangeListener(lowSensitivitySettingAdjustment$settingsChangeListener$1);
        this.useLowSensitivity = columbusSettings.useLowSensitivity();
        onSensitivityChanged();
    }

    @Override // com.google.android.systemui.columbus.sensors.config.Adjustment
    public float adjustSensitivity(float f) {
        return this.useLowSensitivity ? this.sensorConfiguration.lowSensitivityValue : f;
    }
}
