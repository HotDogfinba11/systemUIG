package com.android.systemui.controls;

import android.content.Context;
import android.content.pm.ServiceInfo;
import com.android.settingslib.applications.DefaultAppInfo;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ControlsServiceInfo.kt */
public final class ControlsServiceInfo extends DefaultAppInfo {
    private final ServiceInfo serviceInfo;

    public final ServiceInfo getServiceInfo() {
        return this.serviceInfo;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ControlsServiceInfo(Context context, ServiceInfo serviceInfo2) {
        super(context, context.getPackageManager(), context.getUserId(), serviceInfo2.getComponentName());
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(serviceInfo2, "serviceInfo");
        this.serviceInfo = serviceInfo2;
    }
}
