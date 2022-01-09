package com.android.wm.shell.bubbles;

import java.util.function.Consumer;

public final /* synthetic */ class BubbleStackView$$ExternalSyntheticLambda39 implements Runnable {
    public final /* synthetic */ BubbleStackView f$0;
    public final /* synthetic */ Consumer f$1;

    public /* synthetic */ BubbleStackView$$ExternalSyntheticLambda39(BubbleStackView bubbleStackView, Consumer consumer) {
        this.f$0 = bubbleStackView;
        this.f$1 = consumer;
    }

    public final void run() {
        this.f$0.lambda$screenshotAnimatingOutBubbleIntoSurface$41(this.f$1);
    }
}
