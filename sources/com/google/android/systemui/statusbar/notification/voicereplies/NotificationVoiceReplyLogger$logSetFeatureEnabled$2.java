package com.google.android.systemui.statusbar.notification.voicereplies;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* access modifiers changed from: package-private */
/* compiled from: NotificationVoiceReplyLogger.kt */
public final class NotificationVoiceReplyLogger$logSetFeatureEnabled$2 extends Lambda implements Function1<LogMessage, String> {
    public static final NotificationVoiceReplyLogger$logSetFeatureEnabled$2 INSTANCE = new NotificationVoiceReplyLogger$logSetFeatureEnabled$2();

    NotificationVoiceReplyLogger$logSetFeatureEnabled$2() {
        super(1);
    }

    public final String invoke(LogMessage logMessage) {
        Intrinsics.checkNotNullParameter(logMessage, "$this$log");
        return "BINDER: setFeatureEnabled [userId=" + logMessage.getInt1() + ", enabledSetting=" + logMessage.getInt2() + ']';
    }
}
