package com.android.wm.shell.bubbles;

import java.util.function.Consumer;

public final /* synthetic */ class BubbleController$BubblesImpl$$ExternalSyntheticLambda20 implements Runnable {
    public final /* synthetic */ Consumer f$0;
    public final /* synthetic */ String f$1;

    public /* synthetic */ BubbleController$BubblesImpl$$ExternalSyntheticLambda20(Consumer consumer, String str) {
        this.f$0 = consumer;
        this.f$1 = str;
    }

    public final void run() {
        this.f$0.accept(this.f$1);
    }
}
