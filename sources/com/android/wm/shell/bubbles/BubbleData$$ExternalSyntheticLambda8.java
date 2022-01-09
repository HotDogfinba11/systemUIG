package com.android.wm.shell.bubbles;

import java.util.function.Predicate;

public final /* synthetic */ class BubbleData$$ExternalSyntheticLambda8 implements Predicate {
    public final /* synthetic */ String f$0;

    public /* synthetic */ BubbleData$$ExternalSyntheticLambda8(String str) {
        this.f$0 = str;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return BubbleData.lambda$removeBubblesWithPackageName$2(this.f$0, (Bubble) obj);
    }
}
