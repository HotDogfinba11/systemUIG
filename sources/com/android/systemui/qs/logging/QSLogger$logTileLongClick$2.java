package com.android.systemui.qs.logging;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* access modifiers changed from: package-private */
/* compiled from: QSLogger.kt */
public final class QSLogger$logTileLongClick$2 extends Lambda implements Function1<LogMessage, String> {
    public static final QSLogger$logTileLongClick$2 INSTANCE = new QSLogger$logTileLongClick$2();

    QSLogger$logTileLongClick$2() {
        super(1);
    }

    public final String invoke(LogMessage logMessage) {
        Intrinsics.checkNotNullParameter(logMessage, "$this$log");
        return '[' + ((Object) logMessage.getStr1()) + "] Tile long clicked. StatusBarState=" + ((Object) logMessage.getStr2()) + ". TileState=" + ((Object) logMessage.getStr3());
    }
}
