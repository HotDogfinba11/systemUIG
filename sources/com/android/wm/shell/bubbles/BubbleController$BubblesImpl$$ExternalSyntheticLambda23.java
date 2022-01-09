package com.android.wm.shell.bubbles;

import com.android.wm.shell.bubbles.BubbleController;
import java.util.concurrent.Executor;
import java.util.function.IntConsumer;

public final /* synthetic */ class BubbleController$BubblesImpl$$ExternalSyntheticLambda23 implements IntConsumer {
    public final /* synthetic */ Executor f$0;
    public final /* synthetic */ IntConsumer f$1;

    public /* synthetic */ BubbleController$BubblesImpl$$ExternalSyntheticLambda23(Executor executor, IntConsumer intConsumer) {
        this.f$0 = executor;
        this.f$1 = intConsumer;
    }

    public final void accept(int i) {
        BubbleController.BubblesImpl.lambda$handleDismissalInterception$10(this.f$0, this.f$1, i);
    }
}
