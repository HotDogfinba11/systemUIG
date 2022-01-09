package com.android.wm.shell.bubbles;

import java.util.function.Consumer;

public final /* synthetic */ class BubbleData$$ExternalSyntheticLambda3 implements Consumer {
    public final /* synthetic */ BubbleData f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ BubbleData$$ExternalSyntheticLambda3(BubbleData bubbleData, int i) {
        this.f$0 = bubbleData;
        this.f$1 = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.f$0.lambda$removeBubblesWithPackageName$3(this.f$1, (Bubble) obj);
    }
}
