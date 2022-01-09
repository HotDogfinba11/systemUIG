package com.android.systemui.flags;

import android.os.SystemProperties;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SystemPropertiesHelper.kt */
public class SystemPropertiesHelper {
    public final boolean getBoolean(String str, boolean z) {
        Intrinsics.checkNotNullParameter(str, "name");
        return SystemProperties.getBoolean(str, z);
    }

    public final void set(String str, int i) {
        Intrinsics.checkNotNullParameter(str, "name");
        SystemProperties.set(str, String.valueOf(i));
    }
}
