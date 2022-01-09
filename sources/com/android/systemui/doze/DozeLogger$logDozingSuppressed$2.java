package com.android.systemui.doze;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* access modifiers changed from: package-private */
/* compiled from: DozeLogger.kt */
public final class DozeLogger$logDozingSuppressed$2 extends Lambda implements Function1<LogMessage, String> {
    public static final DozeLogger$logDozingSuppressed$2 INSTANCE = new DozeLogger$logDozingSuppressed$2();

    DozeLogger$logDozingSuppressed$2() {
        super(1);
    }

    public final String invoke(LogMessage logMessage) {
        Intrinsics.checkNotNullParameter(logMessage, "$this$log");
        return Intrinsics.stringPlus("DozingSuppressed=", Boolean.valueOf(logMessage.getBool1()));
    }
}
