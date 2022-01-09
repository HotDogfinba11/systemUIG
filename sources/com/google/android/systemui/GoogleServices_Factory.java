package com.google.android.systemui;

import android.app.AlarmManager;
import android.content.Context;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.statusbar.phone.StatusBar;
import com.google.android.systemui.autorotate.AutorotateDataService;
import com.google.android.systemui.columbus.ColumbusServiceWrapper;
import com.google.android.systemui.elmyra.ServiceConfigurationGoogle;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class GoogleServices_Factory implements Factory<GoogleServices> {
    private final Provider<AlarmManager> alarmManagerProvider;
    private final Provider<AutorotateDataService> autorotateDataServiceProvider;
    private final Provider<ColumbusServiceWrapper> columbusServiceLazyProvider;
    private final Provider<Context> contextProvider;
    private final Provider<ServiceConfigurationGoogle> serviceConfigurationGoogleProvider;
    private final Provider<StatusBar> statusBarProvider;
    private final Provider<UiEventLogger> uiEventLoggerProvider;

    public GoogleServices_Factory(Provider<Context> provider, Provider<ServiceConfigurationGoogle> provider2, Provider<StatusBar> provider3, Provider<UiEventLogger> provider4, Provider<ColumbusServiceWrapper> provider5, Provider<AlarmManager> provider6, Provider<AutorotateDataService> provider7) {
        this.contextProvider = provider;
        this.serviceConfigurationGoogleProvider = provider2;
        this.statusBarProvider = provider3;
        this.uiEventLoggerProvider = provider4;
        this.columbusServiceLazyProvider = provider5;
        this.alarmManagerProvider = provider6;
        this.autorotateDataServiceProvider = provider7;
    }

    @Override // javax.inject.Provider
    public GoogleServices get() {
        return newInstance(this.contextProvider.get(), DoubleCheck.lazy(this.serviceConfigurationGoogleProvider), this.statusBarProvider.get(), this.uiEventLoggerProvider.get(), DoubleCheck.lazy(this.columbusServiceLazyProvider), this.alarmManagerProvider.get(), this.autorotateDataServiceProvider.get());
    }

    public static GoogleServices_Factory create(Provider<Context> provider, Provider<ServiceConfigurationGoogle> provider2, Provider<StatusBar> provider3, Provider<UiEventLogger> provider4, Provider<ColumbusServiceWrapper> provider5, Provider<AlarmManager> provider6, Provider<AutorotateDataService> provider7) {
        return new GoogleServices_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7);
    }

    public static GoogleServices newInstance(Context context, Lazy<ServiceConfigurationGoogle> lazy, StatusBar statusBar, UiEventLogger uiEventLogger, Lazy<ColumbusServiceWrapper> lazy2, AlarmManager alarmManager, AutorotateDataService autorotateDataService) {
        return new GoogleServices(context, lazy, statusBar, uiEventLogger, lazy2, alarmManager, autorotateDataService);
    }
}
