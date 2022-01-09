package com.google.android.systemui.columbus;

import android.content.Context;
import com.android.systemui.settings.UserTracker;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

public final class ColumbusStructuredDataManager_Factory implements Factory<ColumbusStructuredDataManager> {
    private final Provider<Executor> bgExecutorProvider;
    private final Provider<Context> contextProvider;
    private final Provider<UserTracker> userTrackerProvider;

    public ColumbusStructuredDataManager_Factory(Provider<Context> provider, Provider<UserTracker> provider2, Provider<Executor> provider3) {
        this.contextProvider = provider;
        this.userTrackerProvider = provider2;
        this.bgExecutorProvider = provider3;
    }

    @Override // javax.inject.Provider
    public ColumbusStructuredDataManager get() {
        return newInstance(this.contextProvider.get(), this.userTrackerProvider.get(), this.bgExecutorProvider.get());
    }

    public static ColumbusStructuredDataManager_Factory create(Provider<Context> provider, Provider<UserTracker> provider2, Provider<Executor> provider3) {
        return new ColumbusStructuredDataManager_Factory(provider, provider2, provider3);
    }

    public static ColumbusStructuredDataManager newInstance(Context context, UserTracker userTracker, Executor executor) {
        return new ColumbusStructuredDataManager(context, userTracker, executor);
    }
}
