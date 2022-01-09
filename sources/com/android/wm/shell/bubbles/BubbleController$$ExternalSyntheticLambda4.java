package com.android.wm.shell.bubbles;

import com.android.wm.shell.bubbles.Bubbles;

public final /* synthetic */ class BubbleController$$ExternalSyntheticLambda4 implements Bubbles.PendingIntentCanceledListener {
    public final /* synthetic */ BubbleController f$0;

    public /* synthetic */ BubbleController$$ExternalSyntheticLambda4(BubbleController bubbleController) {
        this.f$0 = bubbleController;
    }

    @Override // com.android.wm.shell.bubbles.Bubbles.PendingIntentCanceledListener
    public final void onPendingIntentCanceled(Bubble bubble) {
        this.f$0.lambda$initialize$1(bubble);
    }
}
