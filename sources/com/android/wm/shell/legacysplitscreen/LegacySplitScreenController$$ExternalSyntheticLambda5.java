package com.android.wm.shell.legacysplitscreen;

import java.lang.ref.WeakReference;
import java.util.function.Predicate;

public final /* synthetic */ class LegacySplitScreenController$$ExternalSyntheticLambda5 implements Predicate {
    public final /* synthetic */ boolean f$0;

    public /* synthetic */ LegacySplitScreenController$$ExternalSyntheticLambda5(boolean z) {
        this.f$0 = z;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return LegacySplitScreenController.lambda$updateVisibility$3(this.f$0, (WeakReference) obj);
    }
}
