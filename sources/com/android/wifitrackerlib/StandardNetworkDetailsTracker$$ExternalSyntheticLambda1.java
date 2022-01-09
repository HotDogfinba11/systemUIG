package com.android.wifitrackerlib;

import android.net.wifi.WifiConfiguration;
import java.util.function.Predicate;

public final /* synthetic */ class StandardNetworkDetailsTracker$$ExternalSyntheticLambda1 implements Predicate {
    public final /* synthetic */ StandardNetworkDetailsTracker f$0;

    public /* synthetic */ StandardNetworkDetailsTracker$$ExternalSyntheticLambda1(StandardNetworkDetailsTracker standardNetworkDetailsTracker) {
        this.f$0 = standardNetworkDetailsTracker;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return StandardNetworkDetailsTracker.m463$r8$lambda$VNJxBfZi5neJn83Hm7xOwKCs(this.f$0, (WifiConfiguration) obj);
    }
}
