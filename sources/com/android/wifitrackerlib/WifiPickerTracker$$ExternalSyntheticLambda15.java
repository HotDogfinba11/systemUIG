package com.android.wifitrackerlib;

import java.util.function.Predicate;

public final /* synthetic */ class WifiPickerTracker$$ExternalSyntheticLambda15 implements Predicate {
    public final /* synthetic */ WifiPickerTracker f$0;

    public /* synthetic */ WifiPickerTracker$$ExternalSyntheticLambda15(WifiPickerTracker wifiPickerTracker) {
        this.f$0 = wifiPickerTracker;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return this.f$0.lambda$updateWifiConfigurations$22((StandardWifiEntry) obj);
    }
}
