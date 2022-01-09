package com.google.android.systemui.dagger;

import android.os.IThermalService;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class SystemUIGoogleModule_ProvideIThermalServiceFactory implements Factory<IThermalService> {

    /* access modifiers changed from: private */
    public static final class InstanceHolder {
        private static final SystemUIGoogleModule_ProvideIThermalServiceFactory INSTANCE = new SystemUIGoogleModule_ProvideIThermalServiceFactory();
    }

    @Override // javax.inject.Provider
    public IThermalService get() {
        return provideIThermalService();
    }

    public static SystemUIGoogleModule_ProvideIThermalServiceFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static IThermalService provideIThermalService() {
        return (IThermalService) Preconditions.checkNotNullFromProvides(SystemUIGoogleModule.provideIThermalService());
    }
}
