package com.google.android.systemui.smartspace;

import android.content.ComponentName;
import android.content.Context;
import com.android.systemui.statusbar.FeatureFlags;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: KeyguardSmartspaceController.kt */
public final class KeyguardSmartspaceController {
    private final Context context;
    private final FeatureFlags featureFlags;
    private final KeyguardMediaViewController mediaController;
    private final KeyguardZenAlarmViewController zenController;

    public KeyguardSmartspaceController(Context context2, FeatureFlags featureFlags2, KeyguardZenAlarmViewController keyguardZenAlarmViewController, KeyguardMediaViewController keyguardMediaViewController) {
        Intrinsics.checkNotNullParameter(context2, "context");
        Intrinsics.checkNotNullParameter(featureFlags2, "featureFlags");
        Intrinsics.checkNotNullParameter(keyguardZenAlarmViewController, "zenController");
        Intrinsics.checkNotNullParameter(keyguardMediaViewController, "mediaController");
        this.context = context2;
        this.featureFlags = featureFlags2;
        this.zenController = keyguardZenAlarmViewController;
        this.mediaController = keyguardMediaViewController;
        if (!featureFlags2.isSmartspaceEnabled()) {
            context2.getPackageManager().setComponentEnabledSetting(new ComponentName("com.android.systemui", "com.google.android.systemui.keyguard.KeyguardSliceProviderGoogle"), 1, 1);
            return;
        }
        keyguardZenAlarmViewController.init();
        keyguardMediaViewController.init();
    }
}
