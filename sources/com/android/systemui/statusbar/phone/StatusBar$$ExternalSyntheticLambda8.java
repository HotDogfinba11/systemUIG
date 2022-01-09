package com.android.systemui.statusbar.phone;

import com.android.wm.shell.bubbles.Bubbles;

public final /* synthetic */ class StatusBar$$ExternalSyntheticLambda8 implements Bubbles.BubbleExpandListener {
    public final /* synthetic */ StatusBar f$0;

    public /* synthetic */ StatusBar$$ExternalSyntheticLambda8(StatusBar statusBar) {
        this.f$0 = statusBar;
    }

    @Override // com.android.wm.shell.bubbles.Bubbles.BubbleExpandListener
    public final void onBubbleExpandChanged(boolean z, String str) {
        this.f$0.lambda$new$1(z, str);
    }
}
