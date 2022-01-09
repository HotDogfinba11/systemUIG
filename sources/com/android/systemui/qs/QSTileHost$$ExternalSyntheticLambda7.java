package com.android.systemui.qs;

import java.util.List;
import java.util.function.Predicate;

public final /* synthetic */ class QSTileHost$$ExternalSyntheticLambda7 implements Predicate {
    public final /* synthetic */ String f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ QSTileHost$$ExternalSyntheticLambda7(String str, int i) {
        this.f$0 = str;
        this.f$1 = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return QSTileHost.lambda$addTile$6(this.f$0, this.f$1, (List) obj);
    }
}
