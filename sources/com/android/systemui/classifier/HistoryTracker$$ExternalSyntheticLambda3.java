package com.android.systemui.classifier;

import com.android.systemui.classifier.HistoryTracker;
import java.util.function.Function;

public final /* synthetic */ class HistoryTracker$$ExternalSyntheticLambda3 implements Function {
    public final /* synthetic */ double f$0;

    public /* synthetic */ HistoryTracker$$ExternalSyntheticLambda3(double d) {
        this.f$0 = d;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return HistoryTracker.lambda$falseConfidence$2(this.f$0, (HistoryTracker.CombinedResult) obj);
    }
}
