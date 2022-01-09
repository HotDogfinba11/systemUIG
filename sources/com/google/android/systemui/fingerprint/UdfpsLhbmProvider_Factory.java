package com.google.android.systemui.fingerprint;

import dagger.internal.Factory;

public final class UdfpsLhbmProvider_Factory implements Factory<UdfpsLhbmProvider> {

    /* access modifiers changed from: private */
    public static final class InstanceHolder {
        private static final UdfpsLhbmProvider_Factory INSTANCE = new UdfpsLhbmProvider_Factory();
    }

    @Override // javax.inject.Provider
    public UdfpsLhbmProvider get() {
        return newInstance();
    }

    public static UdfpsLhbmProvider_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static UdfpsLhbmProvider newInstance() {
        return new UdfpsLhbmProvider();
    }
}
