package com.android.systemui.doze;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* access modifiers changed from: package-private */
/* compiled from: DozeLogger.kt */
public final class DozeLogger$logScreenOn$2 extends Lambda implements Function1<LogMessage, String> {
    public static final DozeLogger$logScreenOn$2 INSTANCE = new DozeLogger$logScreenOn$2();

    DozeLogger$logScreenOn$2() {
        super(1);
    }

    public final String invoke(LogMessage logMessage) {
        Intrinsics.checkNotNullParameter(logMessage, "$this$log");
        return Intrinsics.stringPlus("Screen on, pulsing=", Boolean.valueOf(logMessage.getBool1()));
    }
}
