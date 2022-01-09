package com.android.systemui.qs.logging;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* access modifiers changed from: package-private */
/* compiled from: QSLogger.kt */
public final class QSLogger$logTileClick$2 extends Lambda implements Function1<LogMessage, String> {
    public static final QSLogger$logTileClick$2 INSTANCE = new QSLogger$logTileClick$2();

    QSLogger$logTileClick$2() {
        super(1);
    }

    public final String invoke(LogMessage logMessage) {
        Intrinsics.checkNotNullParameter(logMessage, "$this$log");
        return '[' + ((Object) logMessage.getStr1()) + "] Tile clicked. StatusBarState=" + ((Object) logMessage.getStr2()) + ". TileState=" + ((Object) logMessage.getStr3());
    }
}
