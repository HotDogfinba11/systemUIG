package com.android.systemui.wmshell;

import com.android.wm.shell.onehanded.OneHanded;
import java.util.function.Consumer;

public final /* synthetic */ class WMShell$$ExternalSyntheticLambda4 implements Consumer {
    public final /* synthetic */ WMShell f$0;

    public /* synthetic */ WMShell$$ExternalSyntheticLambda4(WMShell wMShell) {
        this.f$0 = wMShell;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.f$0.initOneHanded((OneHanded) obj);
    }
}
