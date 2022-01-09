package com.google.android.systemui.columbus;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.settings.UserTracker;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class ColumbusTargetRequestService_Factory implements Factory<ColumbusTargetRequestService> {
    private final Provider<ColumbusSettings> columbusSettingsProvider;
    private final Provider<ColumbusStructuredDataManager> columbusStructuredDataManagerProvider;
    private final Provider<Looper> looperProvider;
    private final Provider<Handler> mainHandlerProvider;
    private final Provider<Context> sysUIContextProvider;
    private final Provider<UiEventLogger> uiEventLoggerProvider;
    private final Provider<UserTracker> userTrackerProvider;

    public ColumbusTargetRequestService_Factory(Provider<Context> provider, Provider<UserTracker> provider2, Provider<ColumbusSettings> provider3, Provider<ColumbusStructuredDataManager> provider4, Provider<UiEventLogger> provider5, Provider<Handler> provider6, Provider<Looper> provider7) {
        this.sysUIContextProvider = provider;
        this.userTrackerProvider = provider2;
        this.columbusSettingsProvider = provider3;
        this.columbusStructuredDataManagerProvider = provider4;
        this.uiEventLoggerProvider = provider5;
        this.mainHandlerProvider = provider6;
        this.looperProvider = provider7;
    }

    @Override // javax.inject.Provider
    public ColumbusTargetRequestService get() {
        return newInstance(this.sysUIContextProvider.get(), this.userTrackerProvider.get(), this.columbusSettingsProvider.get(), this.columbusStructuredDataManagerProvider.get(), this.uiEventLoggerProvider.get(), this.mainHandlerProvider.get(), this.looperProvider.get());
    }

    public static ColumbusTargetRequestService_Factory create(Provider<Context> provider, Provider<UserTracker> provider2, Provider<ColumbusSettings> provider3, Provider<ColumbusStructuredDataManager> provider4, Provider<UiEventLogger> provider5, Provider<Handler> provider6, Provider<Looper> provider7) {
        return new ColumbusTargetRequestService_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7);
    }

    public static ColumbusTargetRequestService newInstance(Context context, UserTracker userTracker, ColumbusSettings columbusSettings, ColumbusStructuredDataManager columbusStructuredDataManager, UiEventLogger uiEventLogger, Handler handler, Looper looper) {
        return new ColumbusTargetRequestService(context, userTracker, columbusSettings, columbusStructuredDataManager, uiEventLogger, handler, looper);
    }
}
