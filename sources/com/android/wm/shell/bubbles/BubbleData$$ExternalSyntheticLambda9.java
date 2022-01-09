package com.android.wm.shell.bubbles;

import java.util.Set;
import java.util.function.Predicate;

public final /* synthetic */ class BubbleData$$ExternalSyntheticLambda9 implements Predicate {
    public final /* synthetic */ String f$0;
    public final /* synthetic */ Set f$1;

    public /* synthetic */ BubbleData$$ExternalSyntheticLambda9(String str, Set set) {
        this.f$0 = str;
        this.f$1 = set;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return BubbleData.lambda$removeBubblesWithInvalidShortcuts$0(this.f$0, this.f$1, (Bubble) obj);
    }
}
