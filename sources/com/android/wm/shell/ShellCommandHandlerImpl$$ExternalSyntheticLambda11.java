package com.android.wm.shell;

import com.android.wm.shell.splitscreen.SplitScreenController;
import java.util.function.Consumer;

public final /* synthetic */ class ShellCommandHandlerImpl$$ExternalSyntheticLambda11 implements Consumer {
    public final /* synthetic */ Boolean f$0;

    public /* synthetic */ ShellCommandHandlerImpl$$ExternalSyntheticLambda11(Boolean bool) {
        this.f$0 = bool;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ShellCommandHandlerImpl.lambda$runSetSideStageVisibility$11(this.f$0, (SplitScreenController) obj);
    }
}
