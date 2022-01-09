package com.google.android.systemui.columbus.actions;

import android.provider.DeviceConfig;

/* access modifiers changed from: package-private */
/* compiled from: LaunchApp.kt */
public final class LaunchApp$deviceConfigPropertiesChangedListener$1 implements DeviceConfig.OnPropertiesChangedListener {
    final /* synthetic */ LaunchApp this$0;

    LaunchApp$deviceConfigPropertiesChangedListener$1(LaunchApp launchApp) {
        this.this$0 = launchApp;
    }

    public final void onPropertiesChanged(DeviceConfig.Properties properties) {
        if (properties.getKeyset().contains("systemui_google_columbus_secure_deny_list")) {
            this.this$0.updateDenyList(properties.getString("systemui_google_columbus_secure_deny_list", (String) null));
        }
    }
}
