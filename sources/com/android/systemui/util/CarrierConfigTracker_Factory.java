package com.android.systemui.util;

import android.content.Context;
import com.android.systemui.broadcast.BroadcastDispatcher;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class CarrierConfigTracker_Factory implements Factory<CarrierConfigTracker> {
    private final Provider<BroadcastDispatcher> broadcastDispatcherProvider;
    private final Provider<Context> contextProvider;

    public CarrierConfigTracker_Factory(Provider<Context> provider, Provider<BroadcastDispatcher> provider2) {
        this.contextProvider = provider;
        this.broadcastDispatcherProvider = provider2;
    }

    @Override // javax.inject.Provider
    public CarrierConfigTracker get() {
        return newInstance(this.contextProvider.get(), this.broadcastDispatcherProvider.get());
    }

    public static CarrierConfigTracker_Factory create(Provider<Context> provider, Provider<BroadcastDispatcher> provider2) {
        return new CarrierConfigTracker_Factory(provider, provider2);
    }

    public static CarrierConfigTracker newInstance(Context context, BroadcastDispatcher broadcastDispatcher) {
        return new CarrierConfigTracker(context, broadcastDispatcher);
    }
}
