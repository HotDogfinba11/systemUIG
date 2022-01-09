package com.android.systemui.statusbar.commandline;

import java.io.PrintWriter;
import java.util.List;

/* compiled from: CommandRegistry.kt */
public interface Command {
    void execute(PrintWriter printWriter, List<String> list);
}
