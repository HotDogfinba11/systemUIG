package com.android.wifitrackerlib;

import java.util.Map;
import java.util.function.Predicate;

public final /* synthetic */ class SavedNetworkTracker$$ExternalSyntheticLambda6 implements Predicate {
    public final /* synthetic */ Map f$0;

    public /* synthetic */ SavedNetworkTracker$$ExternalSyntheticLambda6(Map map) {
        this.f$0 = map;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return SavedNetworkTracker.$r8$lambda$JmG743l3xWUc2TaQ43bhJ0nM1Xc(this.f$0, (StandardWifiEntry) obj);
    }
}
