package com.android.systemui.dump;

import java.io.PrintWriter;
import java.util.function.Consumer;

/* access modifiers changed from: package-private */
/* compiled from: LogBufferEulogizer.kt */
public final class LogBufferEulogizer$readEulogyIfPresent$1$1 implements Consumer<String> {
    final /* synthetic */ PrintWriter $pw;

    LogBufferEulogizer$readEulogyIfPresent$1$1(PrintWriter printWriter) {
        this.$pw = printWriter;
    }

    public final void accept(String str) {
        this.$pw.println(str);
    }
}
