package com.android.wm.shell.bubbles;

import java.util.ArrayList;
import java.util.function.Consumer;

public final /* synthetic */ class BubbleData$$ExternalSyntheticLambda4 implements Consumer {
    public final /* synthetic */ ArrayList f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ BubbleData$$ExternalSyntheticLambda4(ArrayList arrayList, int i) {
        this.f$0 = arrayList;
        this.f$1 = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        BubbleData.lambda$trim$5(this.f$0, this.f$1, (Bubble) obj);
    }
}
