package com.android.keyguard.clock;

import java.util.List;
import javax.inject.Provider;

public final class ClockOptionsProvider_MembersInjector {
    public static void injectMClockInfosProvider(ClockOptionsProvider clockOptionsProvider, Provider<List<ClockInfo>> provider) {
        clockOptionsProvider.mClockInfosProvider = provider;
    }
}
