package com.google.android.systemui.autorotate;

import android.provider.DeviceConfig;

public final /* synthetic */ class AutorotateDataService$$ExternalSyntheticLambda0 implements DeviceConfig.OnPropertiesChangedListener {
    public final /* synthetic */ AutorotateDataService f$0;

    public /* synthetic */ AutorotateDataService$$ExternalSyntheticLambda0(AutorotateDataService autorotateDataService) {
        this.f$0 = autorotateDataService;
    }

    public final void onPropertiesChanged(DeviceConfig.Properties properties) {
        this.f$0.lambda$init$0(properties);
    }
}
