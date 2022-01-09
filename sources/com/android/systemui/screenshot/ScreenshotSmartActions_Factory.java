package com.android.systemui.screenshot;

import dagger.internal.Factory;

public final class ScreenshotSmartActions_Factory implements Factory<ScreenshotSmartActions> {

    /* access modifiers changed from: private */
    public static final class InstanceHolder {
        private static final ScreenshotSmartActions_Factory INSTANCE = new ScreenshotSmartActions_Factory();
    }

    @Override // javax.inject.Provider
    public ScreenshotSmartActions get() {
        return newInstance();
    }

    public static ScreenshotSmartActions_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static ScreenshotSmartActions newInstance() {
        return new ScreenshotSmartActions();
    }
}
