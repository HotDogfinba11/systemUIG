package com.android.wm.shell.bubbles;

import com.android.wm.shell.bubbles.BubbleController;

public final /* synthetic */ class BubbleController$BubblesImpl$$ExternalSyntheticLambda11 implements Runnable {
    public final /* synthetic */ BubbleController.BubblesImpl f$0;
    public final /* synthetic */ BubbleEntry f$1;

    public /* synthetic */ BubbleController$BubblesImpl$$ExternalSyntheticLambda11(BubbleController.BubblesImpl bubblesImpl, BubbleEntry bubbleEntry) {
        this.f$0 = bubblesImpl;
        this.f$1 = bubbleEntry;
    }

    public final void run() {
        this.f$0.lambda$onEntryRemoved$17(this.f$1);
    }
}
