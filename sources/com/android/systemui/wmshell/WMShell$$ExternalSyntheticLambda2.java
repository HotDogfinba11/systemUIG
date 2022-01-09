package com.android.systemui.wmshell;

import com.android.wm.shell.hidedisplaycutout.HideDisplayCutout;
import java.util.function.Consumer;

public final /* synthetic */ class WMShell$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ WMShell f$0;

    public /* synthetic */ WMShell$$ExternalSyntheticLambda2(WMShell wMShell) {
        this.f$0 = wMShell;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.f$0.initHideDisplayCutout((HideDisplayCutout) obj);
    }
}
