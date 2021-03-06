package com.android.systemui.statusbar.notification.collection.notifcollection;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* access modifiers changed from: package-private */
/* compiled from: NotifCollectionLogger.kt */
public final class NotifCollectionLogger$logDismissOnAlreadyCanceledEntry$2 extends Lambda implements Function1<LogMessage, String> {
    public static final NotifCollectionLogger$logDismissOnAlreadyCanceledEntry$2 INSTANCE = new NotifCollectionLogger$logDismissOnAlreadyCanceledEntry$2();

    NotifCollectionLogger$logDismissOnAlreadyCanceledEntry$2() {
        super(1);
    }

    public final String invoke(LogMessage logMessage) {
        Intrinsics.checkNotNullParameter(logMessage, "$this$log");
        return "Dismiss on " + ((Object) logMessage.getStr1()) + ", which was already canceled. Trying to remove...";
    }
}
