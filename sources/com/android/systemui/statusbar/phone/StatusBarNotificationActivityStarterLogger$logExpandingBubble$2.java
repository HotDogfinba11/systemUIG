package com.android.systemui.statusbar.phone;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* access modifiers changed from: package-private */
/* compiled from: StatusBarNotificationActivityStarterLogger.kt */
public final class StatusBarNotificationActivityStarterLogger$logExpandingBubble$2 extends Lambda implements Function1<LogMessage, String> {
    public static final StatusBarNotificationActivityStarterLogger$logExpandingBubble$2 INSTANCE = new StatusBarNotificationActivityStarterLogger$logExpandingBubble$2();

    StatusBarNotificationActivityStarterLogger$logExpandingBubble$2() {
        super(1);
    }

    public final String invoke(LogMessage logMessage) {
        Intrinsics.checkNotNullParameter(logMessage, "$this$log");
        return "Expanding bubble for " + ((Object) logMessage.getStr1()) + " (rather than firing intent)";
    }
}
