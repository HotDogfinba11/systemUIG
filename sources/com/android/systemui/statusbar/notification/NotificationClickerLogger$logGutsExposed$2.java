package com.android.systemui.statusbar.notification;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* access modifiers changed from: package-private */
/* compiled from: NotificationClickerLogger.kt */
public final class NotificationClickerLogger$logGutsExposed$2 extends Lambda implements Function1<LogMessage, String> {
    public static final NotificationClickerLogger$logGutsExposed$2 INSTANCE = new NotificationClickerLogger$logGutsExposed$2();

    NotificationClickerLogger$logGutsExposed$2() {
        super(1);
    }

    public final String invoke(LogMessage logMessage) {
        Intrinsics.checkNotNullParameter(logMessage, "$this$log");
        return "Ignoring click on " + ((Object) logMessage.getStr1()) + "; guts are exposed";
    }
}
