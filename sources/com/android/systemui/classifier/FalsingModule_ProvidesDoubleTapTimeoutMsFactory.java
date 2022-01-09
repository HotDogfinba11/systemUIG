package com.android.systemui.classifier;

import dagger.internal.Factory;

public final class FalsingModule_ProvidesDoubleTapTimeoutMsFactory implements Factory<Long> {

    /* access modifiers changed from: private */
    public static final class InstanceHolder {
        private static final FalsingModule_ProvidesDoubleTapTimeoutMsFactory INSTANCE = new FalsingModule_ProvidesDoubleTapTimeoutMsFactory();
    }

    @Override // javax.inject.Provider
    public Long get() {
        return Long.valueOf(providesDoubleTapTimeoutMs());
    }

    public static FalsingModule_ProvidesDoubleTapTimeoutMsFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static long providesDoubleTapTimeoutMs() {
        return FalsingModule.providesDoubleTapTimeoutMs();
    }
}
