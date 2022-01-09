package com.android.wifitrackerlib;

import java.util.Map;
import java.util.function.Predicate;

public final /* synthetic */ class WifiPickerTracker$$ExternalSyntheticLambda16 implements Predicate {
    public final /* synthetic */ WifiPickerTracker f$0;

    public /* synthetic */ WifiPickerTracker$$ExternalSyntheticLambda16(WifiPickerTracker wifiPickerTracker) {
        this.f$0 = wifiPickerTracker;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return this.f$0.lambda$updatePasspointConfigurations$24((Map.Entry) obj);
    }
}
