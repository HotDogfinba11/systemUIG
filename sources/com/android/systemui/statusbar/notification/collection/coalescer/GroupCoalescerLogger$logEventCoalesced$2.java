package com.android.systemui.statusbar.notification.collection.coalescer;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* access modifiers changed from: package-private */
/* compiled from: GroupCoalescerLogger.kt */
public final class GroupCoalescerLogger$logEventCoalesced$2 extends Lambda implements Function1<LogMessage, String> {
    public static final GroupCoalescerLogger$logEventCoalesced$2 INSTANCE = new GroupCoalescerLogger$logEventCoalesced$2();

    GroupCoalescerLogger$logEventCoalesced$2() {
        super(1);
    }

    public final String invoke(LogMessage logMessage) {
        Intrinsics.checkNotNullParameter(logMessage, "$this$log");
        return Intrinsics.stringPlus("COALESCED: ", logMessage.getStr1());
    }
}
