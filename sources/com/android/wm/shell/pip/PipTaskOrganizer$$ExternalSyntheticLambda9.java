package com.android.wm.shell.pip;

import android.window.WindowContainerTransaction;
import com.android.wm.shell.legacysplitscreen.LegacySplitScreenController;
import java.util.function.Consumer;

public final /* synthetic */ class PipTaskOrganizer$$ExternalSyntheticLambda9 implements Consumer {
    public final /* synthetic */ PipTaskOrganizer f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ WindowContainerTransaction f$2;

    public /* synthetic */ PipTaskOrganizer$$ExternalSyntheticLambda9(PipTaskOrganizer pipTaskOrganizer, int i, WindowContainerTransaction windowContainerTransaction) {
        this.f$0 = pipTaskOrganizer;
        this.f$1 = i;
        this.f$2 = windowContainerTransaction;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.f$0.lambda$applyWindowingModeChangeOnExit$2(this.f$1, this.f$2, (LegacySplitScreenController) obj);
    }
}
