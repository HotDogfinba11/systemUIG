package com.android.systemui.qs;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public final /* synthetic */ class QSTileHost$$ExternalSyntheticLambda8 implements Predicate {
    public final /* synthetic */ Collection f$0;

    public /* synthetic */ QSTileHost$$ExternalSyntheticLambda8(Collection collection) {
        this.f$0 = collection;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return ((List) obj).removeAll(this.f$0);
    }
}
