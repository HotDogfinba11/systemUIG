package com.android.wm.shell;

import com.android.wm.shell.splitscreen.SplitScreenController;
import java.util.function.Consumer;

public final /* synthetic */ class ShellCommandHandlerImpl$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int f$0;

    public /* synthetic */ ShellCommandHandlerImpl$$ExternalSyntheticLambda1(int i) {
        this.f$0 = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((SplitScreenController) obj).removeFromSideStage(this.f$0);
    }
}
