package com.android.systemui.assist;

import android.provider.DeviceConfig;
import com.android.systemui.DejankUtils;

public class DeviceConfigHelper {
    public long getLong(String str, long j) {
        return ((Long) DejankUtils.whitelistIpcs(new DeviceConfigHelper$$ExternalSyntheticLambda0(str, j))).longValue();
    }

    /* access modifiers changed from: private */
    public static /* synthetic */ Long lambda$getLong$0(String str, long j) {
        return Long.valueOf(DeviceConfig.getLong("systemui", str, j));
    }
}
