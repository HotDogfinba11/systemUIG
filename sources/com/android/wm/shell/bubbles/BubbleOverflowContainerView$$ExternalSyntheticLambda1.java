package com.android.wm.shell.bubbles;

import java.util.function.Consumer;

public final /* synthetic */ class BubbleOverflowContainerView$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ BubbleController f$0;

    public /* synthetic */ BubbleOverflowContainerView$$ExternalSyntheticLambda1(BubbleController bubbleController) {
        this.f$0 = bubbleController;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.f$0.promoteBubbleFromOverflow((Bubble) obj);
    }
}
