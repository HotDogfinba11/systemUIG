package com.google.android.systemui.dagger;

import com.android.systemui.dagger.SysUIComponent;
import com.google.android.systemui.smartspace.KeyguardSmartspaceController;

public interface SysUIGoogleSysUIComponent extends SysUIComponent {

    public interface Builder extends SysUIComponent.Builder {
    }

    KeyguardSmartspaceController createKeyguardSmartspaceController();
}
