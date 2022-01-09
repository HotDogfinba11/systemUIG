package com.android.wm.shell.bubbles;

import com.android.wm.shell.bubbles.Bubbles;

public final /* synthetic */ class BubbleController$$ExternalSyntheticLambda3 implements Bubbles.BubbleExpandListener {
    public final /* synthetic */ Bubbles.BubbleExpandListener f$0;

    public /* synthetic */ BubbleController$$ExternalSyntheticLambda3(Bubbles.BubbleExpandListener bubbleExpandListener) {
        this.f$0 = bubbleExpandListener;
    }

    @Override // com.android.wm.shell.bubbles.Bubbles.BubbleExpandListener
    public final void onBubbleExpandChanged(boolean z, String str) {
        BubbleController.lambda$setExpandListener$6(this.f$0, z, str);
    }
}
