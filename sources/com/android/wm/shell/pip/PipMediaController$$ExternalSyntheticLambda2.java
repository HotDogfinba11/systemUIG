package com.android.wm.shell.pip;

import com.android.wm.shell.pip.PipMediaController;
import java.util.List;
import java.util.function.Consumer;

public final /* synthetic */ class PipMediaController$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ List f$0;

    public /* synthetic */ PipMediaController$$ExternalSyntheticLambda2(List list) {
        this.f$0 = list;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((PipMediaController.ActionListener) obj).onMediaActionsChanged(this.f$0);
    }
}
