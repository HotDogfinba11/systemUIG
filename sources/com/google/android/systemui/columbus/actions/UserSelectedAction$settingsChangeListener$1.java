package com.google.android.systemui.columbus.actions;

import android.util.Log;
import com.google.android.systemui.columbus.ColumbusSettings;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UserSelectedAction.kt */
public final class UserSelectedAction$settingsChangeListener$1 implements ColumbusSettings.ColumbusSettingsChangeListener {
    final /* synthetic */ UserSelectedAction this$0;

    UserSelectedAction$settingsChangeListener$1(UserSelectedAction userSelectedAction) {
        this.this$0 = userSelectedAction;
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
    public void onLowSensitivityChange(boolean z) {
        ColumbusSettings.ColumbusSettingsChangeListener.DefaultImpls.onLowSensitivityChange(this, z);
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
    public void onSelectedActionChange(String str) {
        Intrinsics.checkNotNullParameter(str, "selectedAction");
        UserAction userAction = this.this$0.getActionFromSetting(str);
        if (!Intrinsics.areEqual(userAction, this.this$0.currentAction)) {
            this.this$0.currentAction.onGestureDetected(0, null);
            this.this$0.currentAction = userAction;
            Log.i("Columbus/SelectedAction", Intrinsics.stringPlus("User Action selected: ", this.this$0.currentAction));
            this.this$0.updateAvailable();
        }
    }
}
