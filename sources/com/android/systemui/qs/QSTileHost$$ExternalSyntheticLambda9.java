package com.android.systemui.qs;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public final /* synthetic */ class QSTileHost$$ExternalSyntheticLambda9 implements Predicate {
    public final /* synthetic */ List f$0;

    public /* synthetic */ QSTileHost$$ExternalSyntheticLambda9(List list) {
        this.f$0 = list;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return QSTileHost.lambda$onTuningChanged$2(this.f$0, (Map.Entry) obj);
    }
}
