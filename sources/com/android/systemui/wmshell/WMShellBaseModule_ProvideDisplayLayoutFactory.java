package com.android.systemui.wmshell;

import com.android.wm.shell.common.DisplayLayout;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class WMShellBaseModule_ProvideDisplayLayoutFactory implements Factory<DisplayLayout> {

    /* access modifiers changed from: private */
    public static final class InstanceHolder {
        private static final WMShellBaseModule_ProvideDisplayLayoutFactory INSTANCE = new WMShellBaseModule_ProvideDisplayLayoutFactory();
    }

    @Override // javax.inject.Provider
    public DisplayLayout get() {
        return provideDisplayLayout();
    }

    public static WMShellBaseModule_ProvideDisplayLayoutFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static DisplayLayout provideDisplayLayout() {
        return (DisplayLayout) Preconditions.checkNotNullFromProvides(WMShellBaseModule.provideDisplayLayout());
    }
}
