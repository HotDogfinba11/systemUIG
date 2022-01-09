package com.google.android.systemui.columbus;

import android.os.Handler;
import com.android.systemui.settings.UserTracker;
import com.google.android.systemui.columbus.ColumbusContentObserver;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

public final class ColumbusContentObserver_Factory_Factory implements Factory<ColumbusContentObserver.Factory> {
    private final Provider<ContentResolverWrapper> contentResolverProvider;
    private final Provider<Executor> executorProvider;
    private final Provider<Handler> handlerProvider;
    private final Provider<UserTracker> userTrackerProvider;

    public ColumbusContentObserver_Factory_Factory(Provider<ContentResolverWrapper> provider, Provider<UserTracker> provider2, Provider<Handler> provider3, Provider<Executor> provider4) {
        this.contentResolverProvider = provider;
        this.userTrackerProvider = provider2;
        this.handlerProvider = provider3;
        this.executorProvider = provider4;
    }

    @Override // javax.inject.Provider
    public ColumbusContentObserver.Factory get() {
        return newInstance(this.contentResolverProvider.get(), this.userTrackerProvider.get(), this.handlerProvider.get(), this.executorProvider.get());
    }

    public static ColumbusContentObserver_Factory_Factory create(Provider<ContentResolverWrapper> provider, Provider<UserTracker> provider2, Provider<Handler> provider3, Provider<Executor> provider4) {
        return new ColumbusContentObserver_Factory_Factory(provider, provider2, provider3, provider4);
    }

    public static ColumbusContentObserver.Factory newInstance(ContentResolverWrapper contentResolverWrapper, UserTracker userTracker, Handler handler, Executor executor) {
        return new ColumbusContentObserver.Factory(contentResolverWrapper, userTracker, handler, executor);
    }
}
