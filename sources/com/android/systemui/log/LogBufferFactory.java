package com.android.systemui.log;

import com.android.systemui.dump.DumpManager;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: LogBufferFactory.kt */
public final class LogBufferFactory {
    private final DumpManager dumpManager;
    private final LogcatEchoTracker logcatEchoTracker;

    public final LogBuffer create(String str, int i) {
        Intrinsics.checkNotNullParameter(str, "name");
        return create$default(this, str, i, 0, 4, null);
    }

    public LogBufferFactory(DumpManager dumpManager2, LogcatEchoTracker logcatEchoTracker2) {
        Intrinsics.checkNotNullParameter(dumpManager2, "dumpManager");
        Intrinsics.checkNotNullParameter(logcatEchoTracker2, "logcatEchoTracker");
        this.dumpManager = dumpManager2;
        this.logcatEchoTracker = logcatEchoTracker2;
    }

    public static /* synthetic */ LogBuffer create$default(LogBufferFactory logBufferFactory, String str, int i, int i2, int i3, Object obj) {
        if ((i3 & 4) != 0) {
            i2 = 10;
        }
        return logBufferFactory.create(str, i, i2);
    }

    public final LogBuffer create(String str, int i, int i2) {
        Intrinsics.checkNotNullParameter(str, "name");
        LogBuffer logBuffer = new LogBuffer(str, i, i2, this.logcatEchoTracker);
        this.dumpManager.registerBuffer(str, logBuffer);
        return logBuffer;
    }
}
