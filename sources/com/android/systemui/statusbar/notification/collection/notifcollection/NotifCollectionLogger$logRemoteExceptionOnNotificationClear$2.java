package com.android.systemui.statusbar.notification.collection.notifcollection;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* access modifiers changed from: package-private */
/* compiled from: NotifCollectionLogger.kt */
public final class NotifCollectionLogger$logRemoteExceptionOnNotificationClear$2 extends Lambda implements Function1<LogMessage, String> {
    public static final NotifCollectionLogger$logRemoteExceptionOnNotificationClear$2 INSTANCE = new NotifCollectionLogger$logRemoteExceptionOnNotificationClear$2();

    NotifCollectionLogger$logRemoteExceptionOnNotificationClear$2() {
        super(1);
    }

    public final String invoke(LogMessage logMessage) {
        Intrinsics.checkNotNullParameter(logMessage, "$this$log");
        return "RemoteException while attempting to clear " + ((Object) logMessage.getStr1()) + ":\n" + ((Object) logMessage.getStr2());
    }
}
