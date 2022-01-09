package com.android.wm.shell.bubbles;

import android.content.LocusId;
import com.android.wm.shell.ShellTaskOrganizer;

public final /* synthetic */ class BubbleController$$ExternalSyntheticLambda0 implements ShellTaskOrganizer.LocusIdListener {
    public final /* synthetic */ BubbleController f$0;

    public /* synthetic */ BubbleController$$ExternalSyntheticLambda0(BubbleController bubbleController) {
        this.f$0 = bubbleController;
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.LocusIdListener
    public final void onVisibilityChanged(int i, LocusId locusId, boolean z) {
        this.f$0.lambda$initialize$2(i, locusId, z);
    }
}
