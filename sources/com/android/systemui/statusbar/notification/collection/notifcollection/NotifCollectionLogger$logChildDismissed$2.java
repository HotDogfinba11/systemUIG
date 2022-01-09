package com.android.systemui.statusbar.notification.collection.notifcollection;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* access modifiers changed from: package-private */
/* compiled from: NotifCollectionLogger.kt */
public final class NotifCollectionLogger$logChildDismissed$2 extends Lambda implements Function1<LogMessage, String> {
    public static final NotifCollectionLogger$logChildDismissed$2 INSTANCE = new NotifCollectionLogger$logChildDismissed$2();

    NotifCollectionLogger$logChildDismissed$2() {
        super(1);
    }

    public final String invoke(LogMessage logMessage) {
        Intrinsics.checkNotNullParameter(logMessage, "$this$log");
        return Intrinsics.stringPlus("CHILD DISMISSED (inferred): ", logMessage.getStr1());
    }
}
