package com.android.systemui.statusbar.notification;

import android.content.Context;
import com.android.systemui.util.DeviceConfigProxy;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ForegroundServiceDismissalFeatureController.kt */
public final class ForegroundServiceDismissalFeatureController {
    private final Context context;
    private final DeviceConfigProxy proxy;

    public ForegroundServiceDismissalFeatureController(DeviceConfigProxy deviceConfigProxy, Context context2) {
        Intrinsics.checkNotNullParameter(deviceConfigProxy, "proxy");
        Intrinsics.checkNotNullParameter(context2, "context");
        this.proxy = deviceConfigProxy;
        this.context = context2;
    }

    public final boolean isForegroundServiceDismissalEnabled() {
        return ForegroundServiceDismissalFeatureControllerKt.access$isEnabled(this.proxy);
    }
}
