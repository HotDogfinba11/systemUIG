package androidx.tracing;

import android.os.Trace;

/* access modifiers changed from: package-private */
public final class TraceApi18Impl {
    public static void beginSection(String str) {
        Trace.beginSection(str);
    }

    public static void endSection() {
        Trace.endSection();
    }
}
