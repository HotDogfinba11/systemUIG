package com.android.wm.shell;

import com.android.wm.shell.pip.Pip;
import java.io.PrintWriter;
import java.util.function.Consumer;

public final /* synthetic */ class ShellCommandHandlerImpl$$ExternalSyntheticLambda9 implements Consumer {
    public final /* synthetic */ PrintWriter f$0;

    public /* synthetic */ ShellCommandHandlerImpl$$ExternalSyntheticLambda9(PrintWriter printWriter) {
        this.f$0 = printWriter;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((Pip) obj).dump(this.f$0);
    }
}
