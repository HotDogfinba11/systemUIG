package com.google.android.systemui.columbus;

import android.content.Context;
import com.android.systemui.settings.UserTracker;
import com.google.android.systemui.columbus.ColumbusContentObserver;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class ColumbusSettings_Factory implements Factory<ColumbusSettings> {
    private final Provider<ColumbusContentObserver.Factory> contentObserverFactoryProvider;
    private final Provider<Context> contextProvider;
    private final Provider<UserTracker> userTrackerProvider;

    public ColumbusSettings_Factory(Provider<Context> provider, Provider<UserTracker> provider2, Provider<ColumbusContentObserver.Factory> provider3) {
        this.contextProvider = provider;
        this.userTrackerProvider = provider2;
        this.contentObserverFactoryProvider = provider3;
    }

    @Override // javax.inject.Provider
    public ColumbusSettings get() {
        return newInstance(this.contextProvider.get(), this.userTrackerProvider.get(), this.contentObserverFactoryProvider.get());
    }

    public static ColumbusSettings_Factory create(Provider<Context> provider, Provider<UserTracker> provider2, Provider<ColumbusContentObserver.Factory> provider3) {
        return new ColumbusSettings_Factory(provider, provider2, provider3);
    }

    public static ColumbusSettings newInstance(Context context, UserTracker userTracker, ColumbusContentObserver.Factory factory) {
        return new ColumbusSettings(context, userTracker, factory);
    }
}
