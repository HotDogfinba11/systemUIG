package com.android.systemui.broadcast.logging;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.text.StringsKt__IndentKt;

/* access modifiers changed from: package-private */
/* compiled from: BroadcastDispatcherLogger.kt */
public final class BroadcastDispatcherLogger$logContextReceiverRegistered$2 extends Lambda implements Function1<LogMessage, String> {
    public static final BroadcastDispatcherLogger$logContextReceiverRegistered$2 INSTANCE = new BroadcastDispatcherLogger$logContextReceiverRegistered$2();

    BroadcastDispatcherLogger$logContextReceiverRegistered$2() {
        super(1);
    }

    public final String invoke(LogMessage logMessage) {
        Intrinsics.checkNotNullParameter(logMessage, "$this$log");
        return StringsKt__IndentKt.trimIndent("\n                Receiver registered with Context for user " + logMessage.getInt1() + ".\n                " + ((Object) logMessage.getStr1()) + "\n            ");
    }
}
