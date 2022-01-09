package com.android.systemui.media;

import android.content.Context;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.util.time.SystemClock;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

public final class MediaDataFilter_Factory implements Factory<MediaDataFilter> {
    private final Provider<BroadcastDispatcher> broadcastDispatcherProvider;
    private final Provider<Context> contextProvider;
    private final Provider<Executor> executorProvider;
    private final Provider<NotificationLockscreenUserManager> lockscreenUserManagerProvider;
    private final Provider<MediaResumeListener> mediaResumeListenerProvider;
    private final Provider<SystemClock> systemClockProvider;

    public MediaDataFilter_Factory(Provider<Context> provider, Provider<BroadcastDispatcher> provider2, Provider<MediaResumeListener> provider3, Provider<NotificationLockscreenUserManager> provider4, Provider<Executor> provider5, Provider<SystemClock> provider6) {
        this.contextProvider = provider;
        this.broadcastDispatcherProvider = provider2;
        this.mediaResumeListenerProvider = provider3;
        this.lockscreenUserManagerProvider = provider4;
        this.executorProvider = provider5;
        this.systemClockProvider = provider6;
    }

    @Override // javax.inject.Provider
    public MediaDataFilter get() {
        return newInstance(this.contextProvider.get(), this.broadcastDispatcherProvider.get(), this.mediaResumeListenerProvider.get(), this.lockscreenUserManagerProvider.get(), this.executorProvider.get(), this.systemClockProvider.get());
    }

    public static MediaDataFilter_Factory create(Provider<Context> provider, Provider<BroadcastDispatcher> provider2, Provider<MediaResumeListener> provider3, Provider<NotificationLockscreenUserManager> provider4, Provider<Executor> provider5, Provider<SystemClock> provider6) {
        return new MediaDataFilter_Factory(provider, provider2, provider3, provider4, provider5, provider6);
    }

    public static MediaDataFilter newInstance(Context context, BroadcastDispatcher broadcastDispatcher, MediaResumeListener mediaResumeListener, NotificationLockscreenUserManager notificationLockscreenUserManager, Executor executor, SystemClock systemClock) {
        return new MediaDataFilter(context, broadcastDispatcher, mediaResumeListener, notificationLockscreenUserManager, executor, systemClock);
    }
}
