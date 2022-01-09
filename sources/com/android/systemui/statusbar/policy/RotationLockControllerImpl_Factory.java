package com.android.systemui.statusbar.policy;

import android.content.Context;
import com.android.systemui.util.settings.SecureSettings;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class RotationLockControllerImpl_Factory implements Factory<RotationLockControllerImpl> {
    private final Provider<Context> contextProvider;
    private final Provider<SecureSettings> secureSettingsProvider;

    public RotationLockControllerImpl_Factory(Provider<Context> provider, Provider<SecureSettings> provider2) {
        this.contextProvider = provider;
        this.secureSettingsProvider = provider2;
    }

    @Override // javax.inject.Provider
    public RotationLockControllerImpl get() {
        return newInstance(this.contextProvider.get(), this.secureSettingsProvider.get());
    }

    public static RotationLockControllerImpl_Factory create(Provider<Context> provider, Provider<SecureSettings> provider2) {
        return new RotationLockControllerImpl_Factory(provider, provider2);
    }

    public static RotationLockControllerImpl newInstance(Context context, SecureSettings secureSettings) {
        return new RotationLockControllerImpl(context, secureSettings);
    }
}
