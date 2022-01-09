package com.android.systemui.navigationbar;

import com.android.wm.shell.pip.Pip;
import java.util.function.Consumer;

public final /* synthetic */ class NavigationBar$$ExternalSyntheticLambda19 implements Consumer {
    public final /* synthetic */ NavigationBarView f$0;

    public /* synthetic */ NavigationBar$$ExternalSyntheticLambda19(NavigationBarView navigationBarView) {
        this.f$0 = navigationBarView;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.f$0.registerPipExclusionBoundsChangeListener((Pip) obj);
    }
}
