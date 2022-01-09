package com.android.wm.shell.bubbles;

import com.android.wm.shell.bubbles.Bubbles;

public final /* synthetic */ class BubbleController$$ExternalSyntheticLambda5 implements Bubbles.SuppressionChangedListener {
    public final /* synthetic */ BubbleController f$0;

    public /* synthetic */ BubbleController$$ExternalSyntheticLambda5(BubbleController bubbleController) {
        this.f$0 = bubbleController;
    }

    @Override // com.android.wm.shell.bubbles.Bubbles.SuppressionChangedListener
    public final void onBubbleNotificationSuppressionChange(Bubble bubble) {
        this.f$0.onBubbleNotificationSuppressionChanged(bubble);
    }
}
