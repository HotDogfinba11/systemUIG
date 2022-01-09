package com.android.systemui.statusbar.phone;

import dagger.internal.Factory;

public final class KeyguardDismissUtil_Factory implements Factory<KeyguardDismissUtil> {

    /* access modifiers changed from: private */
    public static final class InstanceHolder {
        private static final KeyguardDismissUtil_Factory INSTANCE = new KeyguardDismissUtil_Factory();
    }

    @Override // javax.inject.Provider
    public KeyguardDismissUtil get() {
        return newInstance();
    }

    public static KeyguardDismissUtil_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static KeyguardDismissUtil newInstance() {
        return new KeyguardDismissUtil();
    }
}
