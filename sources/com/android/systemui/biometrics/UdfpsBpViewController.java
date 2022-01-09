package com.android.systemui.biometrics;

import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.phone.StatusBar;

/* access modifiers changed from: package-private */
public class UdfpsBpViewController extends UdfpsAnimationViewController<UdfpsBpView> {
    /* access modifiers changed from: package-private */
    @Override // com.android.systemui.biometrics.UdfpsAnimationViewController
    public String getTag() {
        return "UdfpsBpViewController";
    }

    protected UdfpsBpViewController(UdfpsBpView udfpsBpView, StatusBarStateController statusBarStateController, StatusBar statusBar, DumpManager dumpManager) {
        super(udfpsBpView, statusBarStateController, statusBar, dumpManager);
    }
}
