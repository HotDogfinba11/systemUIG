package com.google.android.systemui.columbus;

import dagger.internal.Factory;

public final class ColumbusModule_ProvideTransientGateDurationFactory implements Factory<Long> {

    private static final class InstanceHolder {
        private static final ColumbusModule_ProvideTransientGateDurationFactory INSTANCE = new ColumbusModule_ProvideTransientGateDurationFactory();
    }

    @Override // javax.inject.Provider
    public Long get() {
        return Long.valueOf(provideTransientGateDuration());
    }

    public static ColumbusModule_ProvideTransientGateDurationFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static long provideTransientGateDuration() {
        return ColumbusModule.provideTransientGateDuration();
    }
}
