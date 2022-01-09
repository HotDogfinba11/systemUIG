package com.google.android.systemui.assist.uihints;

import dagger.internal.Factory;

public final class LightnessProvider_Factory implements Factory<LightnessProvider> {

    private static final class InstanceHolder {
        private static final LightnessProvider_Factory INSTANCE = new LightnessProvider_Factory();
    }

    @Override // javax.inject.Provider
    public LightnessProvider get() {
        return newInstance();
    }

    public static LightnessProvider_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static LightnessProvider newInstance() {
        return new LightnessProvider();
    }
}
