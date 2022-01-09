package com.android.systemui.statusbar.notification.collection.listbuilder;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* access modifiers changed from: package-private */
/* compiled from: ShadeListBuilderLogger.kt */
public final class ShadeListBuilderLogger$logParentChangeSuppressed$2 extends Lambda implements Function1<LogMessage, String> {
    public static final ShadeListBuilderLogger$logParentChangeSuppressed$2 INSTANCE = new ShadeListBuilderLogger$logParentChangeSuppressed$2();

    ShadeListBuilderLogger$logParentChangeSuppressed$2() {
        super(1);
    }

    public final String invoke(LogMessage logMessage) {
        Intrinsics.checkNotNullParameter(logMessage, "$this$log");
        return "(Build " + logMessage.getLong1() + ")     Change of parent to '" + ((Object) logMessage.getStr1()) + "' suppressed; keeping parent '" + ((Object) logMessage.getStr2()) + '\'';
    }
}
