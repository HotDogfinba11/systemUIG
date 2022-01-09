package com.android.wm.shell;

import com.android.wm.shell.splitscreen.SplitScreenController;
import java.io.PrintWriter;
import java.util.function.Consumer;

public final /* synthetic */ class ShellCommandHandlerImpl$$ExternalSyntheticLambda10 implements Consumer {
    public final /* synthetic */ PrintWriter f$0;

    public /* synthetic */ ShellCommandHandlerImpl$$ExternalSyntheticLambda10(PrintWriter printWriter) {
        this.f$0 = printWriter;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((SplitScreenController) obj).dump(this.f$0, "");
    }
}
