package com.google.android.systemui.statusbar.notification.voicereplies;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* access modifiers changed from: package-private */
/* compiled from: NotificationVoiceReplyLogger.kt */
public final class NotificationVoiceReplyLogger$logSessionAlreadyInProgress$2 extends Lambda implements Function1<LogMessage, String> {
    public static final NotificationVoiceReplyLogger$logSessionAlreadyInProgress$2 INSTANCE = new NotificationVoiceReplyLogger$logSessionAlreadyInProgress$2();

    NotificationVoiceReplyLogger$logSessionAlreadyInProgress$2() {
        super(1);
    }

    public final String invoke(LogMessage logMessage) {
        Intrinsics.checkNotNullParameter(logMessage, "$this$log");
        return "Session already in progress, notifying handler of failure [handlerId=" + logMessage.getInt1() + ']';
    }
}
