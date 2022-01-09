package com.android.wm.shell.bubbles;

import com.android.wm.shell.bubbles.Bubbles;
import java.util.function.Consumer;

public final /* synthetic */ class BubbleController$$ExternalSyntheticLambda12 implements Consumer {
    public final /* synthetic */ Bubbles.SysuiProxy f$0;

    public /* synthetic */ BubbleController$$ExternalSyntheticLambda12(Bubbles.SysuiProxy sysuiProxy) {
        this.f$0 = sysuiProxy;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.f$0.onUnbubbleConversation((String) obj);
    }
}
