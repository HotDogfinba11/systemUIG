package com.google.android.systemui.columbus;

import com.google.android.systemui.columbus.actions.SettingsAction;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class ColumbusServiceWrapper_Factory implements Factory<ColumbusServiceWrapper> {
    private final Provider<ColumbusService> columbusServiceProvider;
    private final Provider<ColumbusSettings> columbusSettingsProvider;
    private final Provider<ColumbusStructuredDataManager> columbusStructuredDataManagerProvider;
    private final Provider<SettingsAction> settingsActionProvider;

    public ColumbusServiceWrapper_Factory(Provider<ColumbusSettings> provider, Provider<ColumbusService> provider2, Provider<SettingsAction> provider3, Provider<ColumbusStructuredDataManager> provider4) {
        this.columbusSettingsProvider = provider;
        this.columbusServiceProvider = provider2;
        this.settingsActionProvider = provider3;
        this.columbusStructuredDataManagerProvider = provider4;
    }

    @Override // javax.inject.Provider
    public ColumbusServiceWrapper get() {
        return newInstance(this.columbusSettingsProvider.get(), DoubleCheck.lazy(this.columbusServiceProvider), DoubleCheck.lazy(this.settingsActionProvider), DoubleCheck.lazy(this.columbusStructuredDataManagerProvider));
    }

    public static ColumbusServiceWrapper_Factory create(Provider<ColumbusSettings> provider, Provider<ColumbusService> provider2, Provider<SettingsAction> provider3, Provider<ColumbusStructuredDataManager> provider4) {
        return new ColumbusServiceWrapper_Factory(provider, provider2, provider3, provider4);
    }

    public static ColumbusServiceWrapper newInstance(ColumbusSettings columbusSettings, Lazy<ColumbusService> lazy, Lazy<SettingsAction> lazy2, Lazy<ColumbusStructuredDataManager> lazy3) {
        return new ColumbusServiceWrapper(columbusSettings, lazy, lazy2, lazy3);
    }
}
