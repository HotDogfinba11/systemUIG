package com.android.systemui.privacy.logging;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* access modifiers changed from: package-private */
/* compiled from: PrivacyLogger.kt */
public final class PrivacyLogger$logChipVisible$2 extends Lambda implements Function1<LogMessage, String> {
    public static final PrivacyLogger$logChipVisible$2 INSTANCE = new PrivacyLogger$logChipVisible$2();

    PrivacyLogger$logChipVisible$2() {
        super(1);
    }

    public final String invoke(LogMessage logMessage) {
        Intrinsics.checkNotNullParameter(logMessage, "$this$log");
        return Intrinsics.stringPlus("Chip visible: ", Boolean.valueOf(logMessage.getBool1()));
    }
}
