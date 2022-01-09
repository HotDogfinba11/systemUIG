package com.android.wm.shell;

import com.android.wm.shell.hidedisplaycutout.HideDisplayCutoutController;
import java.io.PrintWriter;
import java.util.function.Consumer;

public final /* synthetic */ class ShellCommandHandlerImpl$$ExternalSyntheticLambda6 implements Consumer {
    public final /* synthetic */ PrintWriter f$0;

    public /* synthetic */ ShellCommandHandlerImpl$$ExternalSyntheticLambda6(PrintWriter printWriter) {
        this.f$0 = printWriter;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((HideDisplayCutoutController) obj).dump(this.f$0);
    }
}
