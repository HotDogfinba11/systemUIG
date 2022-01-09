package com.google.android.systemui.columbus.sensors.config;

import com.google.android.systemui.columbus.ColumbusSettings;

/* compiled from: LowSensitivitySettingAdjustment.kt */
public final class LowSensitivitySettingAdjustment$settingsChangeListener$1 implements ColumbusSettings.ColumbusSettingsChangeListener {
    final /* synthetic */ LowSensitivitySettingAdjustment this$0;

    LowSensitivitySettingAdjustment$settingsChangeListener$1(LowSensitivitySettingAdjustment lowSensitivitySettingAdjustment) {
        this.this$0 = lowSensitivitySettingAdjustment;
    }

    @Override // com.google.android.systemui.columbus.ColumbusSettings.ColumbusSettingsChangeListener
    public void onAlertSilenceEnabledChange(boolean z) {
        ColumbusSettings.ColumbusSettingsChangeListener.DefaultImpls.onAlertSilenceEnabledChange(this, z);
    }

    @Override // com.google.android.systemui.columbus.ColumbusSettings.ColumbusSettingsChangeListener
    public void onColumbusEnabledChange(boolean z) {
        ColumbusSettings.ColumbusSettingsChangeListener.DefaultImpls.onColumbusEnabledChange(this, z);
    }

    @Override // com.google.android.systemui.columbus.ColumbusSettings.ColumbusSettingsChangeListener
    public void onSelectedActionChange(String str) {
        ColumbusSettings.ColumbusSettingsChangeListener.DefaultImpls.onSelectedActionChange(this, str);
    }

    @Override // com.google.android.systemui.columbus.ColumbusSettings.ColumbusSettingsChangeListener
    public void onSelectedAppChange(String str) {
        ColumbusSettings.ColumbusSettingsChangeListener.DefaultImpls.onSelectedAppChange(this, str);
    }

    @Override // com.google.android.systemui.columbus.ColumbusSettings.ColumbusSettingsChangeListener
    public void onSelectedAppShortcutChange(String str) {
        ColumbusSettings.ColumbusSettingsChangeListener.DefaultImpls.onSelectedAppShortcutChange(this, str);
    }

    @Override // com.google.android.systemui.columbus.ColumbusSettings.ColumbusSettingsChangeListener
    public void onUseApSensorChange(boolean z) {
        ColumbusSettings.ColumbusSettingsChangeListener.DefaultImpls.onUseApSensorChange(this, z);
    }

    @Override // com.google.android.systemui.columbus.ColumbusSettings.ColumbusSettingsChangeListener
    public void onLowSensitivityChange(boolean z) {
        if (this.this$0.useLowSensitivity != z) {
            this.this$0.useLowSensitivity = z;
            this.this$0.onSensitivityChanged();
        }
    }
}
