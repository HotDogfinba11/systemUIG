package com.android.wifitrackerlib;

import java.util.function.Predicate;

public final /* synthetic */ class WifiPickerTracker$$ExternalSyntheticLambda12 implements Predicate {
    public final /* synthetic */ int f$0;

    public /* synthetic */ WifiPickerTracker$$ExternalSyntheticLambda12(int i) {
        this.f$0 = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return WifiPickerTracker.lambda$conditionallyCreateConnectedStandardWifiEntry$26(this.f$0, (Integer) obj);
    }
}